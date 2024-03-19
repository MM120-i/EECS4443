package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * MainActivity class responsible for handling the main functionality of the game.
 * It implements View.OnClickListener to handle button clicks.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScoreManager {

    final static String MYDEBUG = "MYDEBUG";
    public static final int DURATION = 250; // Milliseconds
    protected final int NUMBER_OF_BOXES = 12;
    public static int round = 0;
    public static int highScore = 0;
    private ToneGenerator toneGenerator;
    private Handler handler;
    public Button startButton;
    public Button endGameButton;
    private ButtonClickListener buttonClickListener;

    // Array containing IDs of all buttons in the game
    public static int[] buttonIds = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
            R.id.button11, R.id.button12};
    private boolean isButtonPressInProgress = false;
    private boolean endGameButtonEnabled = true;
    private Vibrator vibrator;
    private static List<Integer> userInputs;

    /**
     * Initializes the activity when created.
     * Sets up the layout, initializes UI components, and prepares the game.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if none exists.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The rules activity
        Button newButton = findViewById(R.id.rule);
        newButton.setOnClickListener(v -> openRulesActivity());

        // Initialize vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initialize ToneGenerator to play button click sounds
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        handler = new Handler();

        // Set up TextView to display current round number
        TextView roundTextView = findViewById(R.id.roundTextView);
        roundTextView.setText("Round: " + round);

        // Set up TextView to display high score
        highScore = getHighScore();
        TextView highScoreTextView = findViewById(R.id.highScoreTextView);
        highScoreTextView.setText("High Score: " + highScore);

        // Initialize start and end button
        startButton = findViewById(R.id.startButton);
        endGameButton = findViewById(R.id.endGameButton);

        List<Button> buttons = new ArrayList<>();

        // Create and initialize game instance
        Game game = new Game(this);
        game.init(this);
        game.setRoundTextView(roundTextView);

        // Initialize user inputs list
        userInputs = new ArrayList<>();

        // Add all the buttons to the list
        for (int i = 1; i <= NUMBER_OF_BOXES; i++) {

            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            button.setOnClickListener(this);
            buttons.add(button);
        }

        // Set click listeners for start and end game buttons
        startButton.setOnClickListener(this);
        endGameButton.setOnClickListener(this);
    }

    /**
     * Opens the RulesActivity to display the rules of the game.
     */
    private void openRulesActivity() {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

    /**
     * Saves the high score to SharedPreferences.
     *
     * @param score The high score to be saved.
     */
    @Override
    public void saveHighScore(int score) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HIGH_SCORE_KEY, score);
        editor.apply();
    }

    /**
     * Retrieves the high score from SharedPreferences.
     *
     * @return The high score retrieved from SharedPreferences, or 0 if not found.
     */
    @Override
    public int getHighScore(){
        SharedPreferences prefs = getSharedPreferences(GameOverActivity.PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(GameOverActivity.HIGH_SCORE_KEY, 0); // Default value is 0
    }

    /**
     * Retrieves the Handler object associated with this activity.
     *
     * @return The Handler object for managing delayed operations.
     */
    public Handler getHandler(){
        return handler;
    }

    /**
     * Retrieves the current round number of the game.
     *
     * @return The current round number.
     */
    public int getCurrentRound(){
        return round;
    }

    /**
     * Sets the listener for button click events.
     *
     * @param listener The ButtonClickListener object to handle button click events.
     */
    public void setButtonClickListener(ButtonClickListener listener){
        this.buttonClickListener = listener;
    }

    /**
     * Handles the click events of buttons in the activity.
     *
     * @param view The View object representing the clicked button.
     */
    @Override
    public void onClick(View view) {

        if (isButtonPressInProgress) {
            return;
        }

        // Vibrate when a button is clicked.
        if(vibrator != null && vibrator.hasVibrator()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else{
                vibrator.vibrate(50);
            }
        }

        int id = view.getId();
        isButtonPressInProgress = true;

        // HashMap to map button IDs to their corresponding tones
        HashMap<Integer, Integer> buttonToneMap = new HashMap<>();

        int[] toneTypes = {ToneGenerator.TONE_DTMF_1, ToneGenerator.TONE_DTMF_2, ToneGenerator.TONE_DTMF_3,
                ToneGenerator.TONE_DTMF_4, ToneGenerator.TONE_DTMF_5, ToneGenerator.TONE_DTMF_6,
                ToneGenerator.TONE_DTMF_7, ToneGenerator.TONE_DTMF_8, ToneGenerator.TONE_DTMF_9,
                ToneGenerator.TONE_DTMF_A, ToneGenerator.TONE_DTMF_B, ToneGenerator.TONE_DTMF_C};

        for (int i = 0; i < buttonIds.length; i++) {
            buttonToneMap.put(buttonIds[i], toneTypes[i]);
        }

        // Get the corresponding tone for the clicked button ID
        Integer toneType = buttonToneMap.get(id);
        if (toneType != null) {
            playTone(toneType);
        }

        // Store user inputs when a button is clicked
        if(id != R.id.startButton && id != R.id.endGameButton){
            userInputs.add(id);
        }

        // Change button colour when clicked.
        final Button clickedButton = findViewById(id);
        int clickedColour = ContextCompat.getColor(getApplicationContext(), R.color.clicked_button_color);
        changeButtonColor(clickedButton, clickedColour, false);
        handler.postDelayed(() -> changeButtonColor(clickedButton, Color.TRANSPARENT, true), DURATION);

        buttonFunctionalities(id);

        isButtonPressInProgress = false;
    }

    /**
     * Handles the functionality associated with button clicks.
     * If the clicked button is not the start button, it enables the start button.
     * If the clicked button is the end game button, it triggers the end game action.
     * If the clicked button is the start button and it is enabled, it triggers the start button action.
     * Otherwise, it disables the start button after clicking it.
     *
     * @param id The ID of the clicked button.
     */
    private void buttonFunctionalities(int id){

        // Enable the start button if the clicked button is not the start button
        if(id != R.id.startButton){
            startButton.setEnabled(true);
        }

        // Trigger the end game action if the clicked button is the end game button
        if (id == R.id.endGameButton) {

            if(endGameButtonEnabled){
                onEndGameButtonClick();
                endGameButtonEnabled = false;
            }
        }     // Trigger the start button action if the clicked button is the start button and it is enabled
        else if (id == R.id.startButton) {

            if (buttonClickListener != null) {

                if (startButton.isEnabled()) {
                    buttonClickListener.onStartButtonClick();
                }
            }
            // Disable the Start button after clicking it
            startButton.setEnabled(false);
        }
    }

    /**
     * Plays a tone using the ToneGenerator with the specified tone type.
     * After playing the tone, it stops the tone after a certain duration.
     *
     * @param toneType The type of tone to be played.
     */
    private void playTone(int toneType) {
        toneGenerator.startTone(toneType);
        handler.postDelayed(() -> toneGenerator.stopTone(), DURATION);
    }

    /**
     * Handles the button click event for the "End Game" button.
     * Starts the GameOverActivity and passes the current round as an extra.
     */
    public void onEndGameButtonClick(){
        Intent gameOverIntent = new Intent(MainActivity.this, GameOverActivity.class);
        gameOverIntent.putExtra("round", round);
        startActivity(gameOverIntent);
    }

    /**
     * Changes the color of the specified button.
     *
     * @param button The button to change the color of.
     * @param color The color to set for the button.
     * @param revert Flag indicating whether to revert the button's color to the default color.
     */
    private void changeButtonColor(View button, int color, boolean revert) {

        runOnUiThread(() -> {

            if (revert) {
                int defaultColor = ContextCompat.getColor(getApplicationContext(), R.color.default_button_color);
                button.getBackground().setColorFilter(defaultColor, PorterDuff.Mode.SRC_ATOP);
            }
            else {
                button.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                handler.postDelayed(() -> changeButtonColor(button, Color.TRANSPARENT, true), DURATION);
            }
        });
    }

    /**
     * Passes the user inputs collected in the MainActivity to the Game class for storage.
     * Clears the userInputs list after passing the inputs to the Game class.
     * This method ensures that the user inputs are stored and processed by the Game logic.
     */
    public static void passUserInputsToGame(){
        Game.storeUserInputs(userInputs);
        userInputs.clear();
    }

    /**
     * Called when the activity is about to be destroyed.
     * Releases the resources used by the ToneGenerator.
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();

        // Release ToneGenerator resources
        if (toneGenerator != null) {
            toneGenerator.release();
            toneGenerator = null;
        }

        // Release vibrator resources and all that stuff
        if(vibrator != null){
            vibrator.cancel();
        }
    }

    /**
     * Called when the activity is no longer visible to the user.
     * Saves the current high score to SharedPreferences before the activity is stopped.
     */
    @Override
    protected void onStop(){
        super.onStop();
        saveHighScore(highScore);
    }
}