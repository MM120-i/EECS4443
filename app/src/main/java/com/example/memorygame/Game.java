package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Static imports
import static com.example.memorygame.MainActivity.DURATION;
import static com.example.memorygame.MainActivity.buttonIds;
import static com.example.memorygame.MainActivity.round;

import androidx.core.content.ContextCompat;

/**
 * Represents the game logic and controls within the Memory Game application. This class handles
 * the generation of game patterns, button clicks, tone generation, and interaction with the main
 * activity.
 */
public class Game implements ButtonClickListener {
    final static String MYDEBUG = "MYDEBUG";
    private final MainActivity activity;
    private final ToneGenerator toneGenerator;
    private TextView roundTextView;
    private boolean startButtonEnabled = true;
    private final int MAX_ROUNDS = 5;   // This can be changed ofc. But for now we r keeping the max laps to 5.

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
     * Handles the action when the "End Game" button is clicked.
     */
    @Override
    public void onEndGameButtonClick() {
        Log.i(MYDEBUG, "End Game button clicked.");
    }

    /**
     * Handles the action when the "Start" button is clicked. It checks if the button is enabled
     * and if the maximum number of rounds has not been reached. If conditions are met, it initiates
     * the game by playing a random pattern. If the maximum rounds are reached, it launches the
     * GameOverActivity to display the final stats.
     */
    @Override
    public void onStartButtonClick() {
        Log.i(MYDEBUG, "Start button clicked.");

        // Check if the Start button is enabled and if the maximum number of rounds has not been reached
        if (startButtonEnabled && round < MAX_ROUNDS) {
            startButtonEnabled = false;
            playRandomPattern();
            activity.getHandler().postDelayed(() -> startButtonEnabled = true, DURATION * 3); // Adjust the delay as needed
        }
        // Launch the GameOverActivity if the maximum rounds are reached
        else if(round >= MAX_ROUNDS){
            Intent intent = new Intent(activity, GameOverActivity.class);
            intent.putExtra("round", round);
            activity.startActivity(intent);
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

        // Initialize variables and lists
        List<Integer> pattern = new ArrayList<>();
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
            pattern.add(buttonId);
            playToneAndChangeColor(buttonId, delay);
            delay += (DURATION + 90);
        }

        // Update the round text view after determining the number of beeps
        updateRoundTextView();

        Log.i(MYDEBUG, "Generated random pattern: " + pattern);
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
            changeButtonColor(buttonId, R.color.clicked_button_color);
            toneGenerator.startTone(toneType);
            activity.getHandler().postDelayed(() -> {
                toneGenerator.stopTone();
                changeButtonColor(buttonId, R.color.default_button_color);
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