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

    public Game(MainActivity activity) {
        this.activity = activity;
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
    }

    public void init(MainActivity activity) {
        activity.setButtonClickListener(this);
    }

    @Override
    public void onEndGameButtonClick() {
        Log.i(MYDEBUG, "End Game button clicked.");
    }

    @Override
    public void onStartButtonClick() {
        Log.i(MYDEBUG, "Start button clicked.");

        // Check if the Start button is enabled
        if (startButtonEnabled && round < MAX_ROUNDS) {
            startButtonEnabled = false;
            playRandomPattern();
            activity.getHandler().postDelayed(() -> startButtonEnabled = true, DURATION * 3); // Adjust the delay as needed
        }
        else if(round >= MAX_ROUNDS){
            Intent intent = new Intent(activity, GameOverActivity.class);
            intent.putExtra("round", round);
            activity.startActivity(intent);
        }
    }

    public void setRoundTextView(TextView textView){
        this.roundTextView = textView;
    }

    private void playRandomPattern() {

        if (round > MAX_ROUNDS) {
            return;
        }

        List<Integer> pattern = new ArrayList<>();
        Random random = new Random();
        round++;
        // Calculate the number of beeps based on the current round
        int numberOfBeeps = 3 + (round - 1);
        int delay = 0;
        int previousButtonId = -1;

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

            pattern.add(buttonId);
            playToneAndChangeColor(buttonId, delay);
            delay += (DURATION + 90);
        }

        // Update the round text view after determining the number of beeps
        updateRoundTextView();

        Log.i(MYDEBUG, "Generated random pattern: " + pattern);
    }

    private void playToneAndChangeColor(int buttonId, int delay) {

        int toneType = mapButtonToTone(buttonId);

        activity.getHandler().postDelayed(() -> {
            changeButtonColor(buttonId, R.color.clicked_button_color);
            toneGenerator.startTone(toneType);
            activity.getHandler().postDelayed(() -> {
                toneGenerator.stopTone();
                changeButtonColor(buttonId, R.color.default_button_color);
            }, DURATION * 2);
        }, delay);
    }

    @SuppressLint("SetTextI18n")
    private void updateRoundTextView(){
        if(roundTextView != null){
            activity.runOnUiThread(() -> roundTextView.setText("Round: " + activity.getCurrentRound()));
        }
    }

    // Method to map a button ID to a tone type
    private int mapButtonToTone(int buttonId) {
        return buttonToToneMap.get(buttonId, ToneGenerator.TONE_DTMF_0);
    }
    private void changeButtonColor(int buttonId, int colorId) {
        activity.runOnUiThread(() -> {
            Button button = activity.findViewById(buttonId);
            button.getBackground().setColorFilter(activity.getResources().getColor(colorId), android.graphics.PorterDuff.Mode.SRC_ATOP);
        });
    }
}
