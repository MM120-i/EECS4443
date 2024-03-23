package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//Static imports
import static com.example.memorygame.MainActivity.DURATION;
import static com.example.memorygame.MainActivity.buttonIds;
import static com.example.memorygame.MainActivity.highScore;
import static com.example.memorygame.MainActivity.round;

import androidx.core.content.ContextCompat;

/**
 * Represents the game logic and controls within the Memory Game application. This class handles
 * the generation of game patterns, button clicks, tone generation, and interaction with the main
 * activity.
 */
public class Game implements ButtonClickListener{

    private final MainActivity activity;
    private final ToneGenerator toneGenerator;
    private TextView roundTextView;
    private boolean startButtonEnabled = true;
    public static long totalTime  = 0;
    public static double errorRate;
    static final List<Integer> userInputList = new ArrayList<>();
    private final List<Integer> allPatterns = new ArrayList<>();
    public static double accuracyRate;
    private final HashMap<Integer, Long> roundStartTimeMap = new HashMap<>();
    public static final int MAX_ROUNDS = 5;   // This can be changed ofc. But for now we r keeping the max rounds to 5.

    // Mapping of button IDs to tone types.
    private static final SparseIntArray buttonToToneMap = new SparseIntArray();

    static {
        buttonToToneMap.put(R.id.button1, ToneGenerator.TONE_DTMF_1);
        buttonToToneMap.put(R.id.button2, ToneGenerator.TONE_DTMF_2);
        buttonToToneMap.put(R.id.button3, ToneGenerator.TONE_DTMF_3);
        buttonToToneMap.put(R.id.button4, ToneGenerator.TONE_DTMF_4);
        buttonToToneMap.put(R.id.button5, ToneGenerator.TONE_DTMF_5);
        buttonToToneMap.put(R.id.button6, ToneGenerator.TONE_DTMF_6);
        buttonToToneMap.put(R.id.button7, ToneGenerator.TONE_DTMF_7);
        buttonToToneMap.put(R.id.button8, ToneGenerator.TONE_DTMF_8);
        buttonToToneMap.put(R.id.button9, ToneGenerator.TONE_DTMF_9);
        buttonToToneMap.put(R.id.button10, ToneGenerator.TONE_DTMF_A);
        buttonToToneMap.put(R.id.button11, ToneGenerator.TONE_DTMF_B);
        buttonToToneMap.put(R.id.button12, ToneGenerator.TONE_DTMF_C);
    }

    /**
     * Constructs a new Game instance with the specified MainActivity.
     *
     * @param activity The MainActivity associated with the game.
     */
    public Game(MainActivity activity) {
        this.activity = activity;
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
    }

    /**
     * Initializes the game by setting the button click listener for the MainActivity.
     *
     * @param activity The MainActivity instance to initialize.
     */
    public void init(MainActivity activity) {
        activity.setButtonClickListener(this);
    }

    /**
     * Handles the action when the "Start" button is clicked. It checks if the button is enabled
     * and if the maximum number of rounds has not been reached. If conditions are met, it initiates
     * the game by playing a random pattern. If the maximum rounds are reached, it launches the
     * GameOverActivity to display the final stats.
     */
    @Override
    public void onStartButtonClick() {

        // Check if the Start button is enabled and if the maximum number of rounds has not been reached
        if (startButtonEnabled && round < MAX_ROUNDS) {

            startButtonEnabled = false;
            roundStartTimeMap.put(round, System.currentTimeMillis());
            playRandomPattern();
            activity.getHandler().postDelayed(() -> startButtonEnabled = true, DURATION * 3);
        }
        // Launch the GameOverActivity if the maximum rounds are reached
        else if (round >= MAX_ROUNDS) {

            // Calculate and pass the average time per round to the GameOverActivity
            int roundsCompleted = 0; // Variable to track completed rounds

            for (int i = 1; i <= round; i++) {

                if (roundStartTimeMap.containsKey(i)) {

                    Long startTime = roundStartTimeMap.get(i);

                    if (startTime != null) {
                        totalTime += System.currentTimeMillis() - startTime;
                        roundsCompleted++;
                    }
                }
            }

            // Calculate average time per round only if laps were completed
            long averageTimePerRound = (roundsCompleted > 0) ? totalTime / roundsCompleted : 0;

            // Launch GameOverActivity with the average time per round and accuracy rate
            Intent intent = new Intent(activity, GameOverActivity.class);
            intent.putExtra("round", round);
            intent.putExtra("high score", highScore);
            intent.putExtra("average_time_per_round", averageTimePerRound);
            intent.putExtra("accuracy_rate", accuracyRate);
            intent.putExtra("error_rate", errorRate);
            activity.startActivity(intent);

            MainActivity.passUserInputsToGame();
            accuracy();

            // Insert game results into the database using try-with-resources
            try (DatabaseHelper dbHelper = new DatabaseHelper(activity.getApplicationContext())) {
                dbHelper.addGameResult(accuracyRate, errorRate, round, averageTimePerRound / 1000);
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                DatabaseLogger.logDatabaseContents(database, GameResultsContract.GameEntry.TABLE_NAME);
            }
            catch (Exception e) {
                e.printStackTrace();
                Log.i("MYDEBUG", "Error inserting into the database.");
            }
        }
    }

    /**
     * Sets the TextView for displaying the current round.
     *
     * @param textView The TextView to display the current round.
     */
    public void setRoundTextView(TextView textView){
        this.roundTextView = textView;
    }

    /**
     * This is the algorithm that plays a random pattern of button clicks each round. If the current round exceeds the maximum
     * allowed rounds, the method returns without generating a pattern.
     */
    private void playRandomPattern() {

        // Check if the current round exceeds the maximum allowed rounds
        if (round > MAX_ROUNDS) {
            return;
        }

        // Initialize
        Random random = new Random();
        round++;

        // Calculate the number of beeps based on the current round
        int numberOfBeeps = 3 + (round - 1);
        int delay = 0;
        int previousButtonId = -1;

        // Generate the random pattern of button clicks
        for (int i = 0; i < numberOfBeeps; i++) {

            int randomIndex;
            int buttonId;

            // Generate a new random button until it is different from the previous one
            do {
                randomIndex = random.nextInt(buttonIds.length);
                buttonId = buttonIds[randomIndex];
            }
            while (buttonId == previousButtonId);

            previousButtonId = buttonId;

            // Add the button ID to the pattern list, play the corresponding tone, and change button color
            playToneAndChangeColor(buttonId, delay);
            delay += (DURATION + 90);

            allPatterns.add(buttonId);
        }

        // Update the round text view after determining the number of beeps
        updateRoundTextView();
    }

    /**
     * Plays a tone and changes the color of the specified button.
     *
     * @param buttonId The ID of the button to play the tone and change its color.
     * @param delay    The delay before playing the tone and changing the color.
     */
    private void playToneAndChangeColor(int buttonId, int delay) {

        int toneType = mapButtonToTone(buttonId);

        // Post a delayed action to change the button color and play the tone
        activity.getHandler().postDelayed(() -> {

            int nightModeFlags = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            boolean isDarkModeEnabled = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
            int defaultButtonColor = isDarkModeEnabled ? R.color.dark_default_button_color : R.color.light_default_button_color;

            changeButtonColor(buttonId, R.color.clicked_button_color);
            toneGenerator.startTone(toneType);
            activity.getHandler().postDelayed(() -> {
                toneGenerator.stopTone();
                changeButtonColor(buttonId, defaultButtonColor);
            }, DURATION * 2);
        }, delay);
    }

    /**
     * Updates the round text view with the current round number.
     * This method is used to display the current round number on the UI.
     */
    @SuppressLint("SetTextI18n")
    private void updateRoundTextView(){
        if(roundTextView != null){
            activity.runOnUiThread(() -> roundTextView.setText("Round: " + activity.getCurrentRound()));
        }
    }

    /**
     * Stores the user inputs in the {@code userInputList}.
     *
     * @param userInput The list of integers representing the user inputs.
     */
    public static void storeUserInputs(List<Integer> userInput){

        if(userInput == null){
            userInput = new ArrayList<>();
        }

        userInputList.addAll(userInput);
    }

    /**
     * Calculates the accuracy rate and error rate based on user inputs and generated patterns.
     * Accuracy rate is calculated as the percentage of correct matches out of total matches.
     * Error rate is calculated as the percentage of incorrect matches out of total matches.
     */
    public void accuracy() {

        int match = 0, notmatch = 0;
        int minLength = Math.min(userInputList.size(), allPatterns.size());

        // Loop through the user inputs and patterns to compare each element
        for (int i = 0; i < minLength; i++) {

            if (userInputList.get(i).equals(allPatterns.get(i))) {
                match++;
            }
            else {
                notmatch++;
            }
        }

        // Calculate the accuracy rate as the percentage of correct matches out of total matches
        accuracyRate = (double) match / allPatterns.size() * 100;

        int totalMatches = match + notmatch;

        // Calculate the error rate as the percentage of incorrect matches out of total matches
        if(totalMatches > 0){
            errorRate = ((double) notmatch / totalMatches) * 100;
        }
        else{
            errorRate = 0.0;
        }

        // Calling the score board method to settle the score.
        scoreBoard();
    }

    /**
     * Calculates the high score based on the accuracy rate based on the following:
     * If the accuracy rate is between 60% and 69%, the high score is set to 1.
     * If the accuracy rate is between 70% and 79%, the high score is set to 2.
     * If the accuracy rate is between 80% and 99%, the high score is set to 3.
     * If the accuracy rate is 100%, the high score is set to 5.
     * Otherwise, the high score is set to 0.
     */
    private void scoreBoard(){

        if(accuracyRate >= 60 && accuracyRate < 70){
            highScore = 1;
        }
        else if(accuracyRate >= 70 && accuracyRate < 80){
            highScore = 2;
        }
        else if(accuracyRate >= 80 && accuracyRate < 100){
            highScore = 3;
        }
        else if(accuracyRate >= 100){
            highScore = 5;
        }
        else{
            highScore = 0;
        }
    }

    /**
     * Maps a button ID to a tone type.
     *
     * @param buttonId The ID of the button to map to a tone type.
     * @return The corresponding tone type mapped to the button ID.
     */
    private int mapButtonToTone(int buttonId) {
        return buttonToToneMap.get(buttonId, ToneGenerator.TONE_DTMF_0);
    }

    /**
     * Changes the color of the specified button.
     *
     * @param buttonId The ID of the button whose color needs to be changed.
     * @param colorId  The resource ID of the color to set for the button.
     */
    private void changeButtonColor(int buttonId, int colorId) {
        activity.runOnUiThread(() -> {
            Button button = activity.findViewById(buttonId);
            button.getBackground().setColorFilter(ContextCompat.getColor(activity, colorId), android.graphics.PorterDuff.Mode.SRC_ATOP);
        });
    }
}