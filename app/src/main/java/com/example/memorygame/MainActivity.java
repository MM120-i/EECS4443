package com.example.memorygame;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.*;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private  ArrayList<Integer> pattern;
    private int currentIndex;
    private ToneGenerator toneGenerator;
    private static final int NUM_BUTTONS = 4; // Can be changed ofc
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        // Set onClickListener for each button
        button1.setOnClickListener(this::onButtonClick);
        button2.setOnClickListener(this::onButtonClick);
        button3.setOnClickListener(this::onButtonClick);
        button4.setOnClickListener(this::onButtonClick);

        // Initialize ToneGenerator
        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        // Initialize pattern and currentIndex
        pattern = new ArrayList<>();
        currentIndex = 0;

        // Generate the initial pattern
        generatePattern();

        // Display the initial pattern only if there is a pattern
        if (!pattern.isEmpty()) {
            displayPattern();
        }
    }

    // Method to generate a random pattern
    private void generatePattern() {
        Random random = new Random();
        pattern.clear();
        for (int i = 0; i < NUM_BUTTONS; i++) {
            // Assign a random button number to each position in the pattern
            pattern.add(random.nextInt(NUM_BUTTONS) + 1);
        }
    }

    // Method to display the current pattern
    private void displayPattern() {
        // Use a handler or TimerTask to light up buttons with delays
        for (int i : pattern) {
            playTone(i);
        }
    }

    private void playTone(int buttonNumber) {
        switch (buttonNumber) {
            case 1:
                handler.postDelayed(() -> toneGenerator.startTone(ToneGenerator.TONE_DTMF_2, 200), 100);
                break;
            case 2:
                handler.postDelayed(() -> toneGenerator.startTone(ToneGenerator.TONE_DTMF_5, 200), 100);
                break;
            case 3:
                handler.postDelayed(() -> toneGenerator.startTone(ToneGenerator.TONE_DTMF_7, 200), 100);
                break;
            case 4:
                handler.postDelayed(() -> toneGenerator.startTone(ToneGenerator.TONE_DTMF_1, 200), 100);
                break;
                // We can add more cases ofc.
            default:
                break;
        }
    }

    // Method called when a button is clicked
    public void onButtonClick(View view) {

        int buttonClicked = Integer.parseInt(view.getTag().toString());

        // Check if the clicked button matches the current position in the pattern
        if (buttonClicked == pattern.get(currentIndex)) {

            currentIndex++;

            // Check if the user has completed the pattern
            if (currentIndex == pattern.size()) {
                // User successfully replicated the pattern, generate a new one
                currentIndex = 0;
                generatePattern();
                displayPattern();
            }
        }
        else {
            // Game over, handle as needed (e.g., display a message, reset the game)
            // You may also want to implement a delay before resetting the game
            currentIndex = 0;
            generatePattern();
            displayPattern();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        toneGenerator.release();
    }
}
