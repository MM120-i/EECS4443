package com.example.memorygame;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static String MYDEBUG = "MYDEBUG";
    protected final int NUMBER_OF_BOXES = 12;
    private ToneGenerator toneGenerator;
    private Handler handler;
    private int round = 0;
    private int highScore = 0;
    public Button startButton;
    public Button nextButton;
    private List<Button> buttons;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        handler = new Handler();

        TextView roundTextView = findViewById(R.id.roundTextView);
        roundTextView.setText("Round: " + round);

        TextView highScoreTextView = findViewById(R.id.highScoreTextView);
        highScoreTextView.setText("High Score " + highScore);

        startButton = findViewById(R.id.startButton);
        nextButton = findViewById(R.id.nextButton);

        buttons = new ArrayList<>();

        // Add all the buttons to the list
        for (int i = 1; i <= NUMBER_OF_BOXES; i++) {
            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            button.setOnClickListener(this);
            buttons.add(button);
        }

        startButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        // Create a HashMap to map button IDs to their corresponding tones
        HashMap<Integer, Integer> buttonToneMap = new HashMap<>();

        int[] buttonIds = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
                R.id.button11, R.id.button12};

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

        // We can maybe add the functionality of the start and next button in another file called Game.java? Where the logic of the game should be implemented.
        if (id == R.id.startButton) {
            Log.i(MYDEBUG, "Start button clicked");
        }
        else if (id == R.id.nextButton) {
            Log.i(MYDEBUG, "Next button clicked");
        }
    }

    private void playTone(int toneType) {
        toneGenerator.startTone(toneType);
        handler.postDelayed(() -> toneGenerator.stopTone(), 180); // Stop tone after 1 second (1000 milliseconds)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release ToneGenerator resources
        if (toneGenerator != null) {
            toneGenerator.release();
            toneGenerator = null;
        }
    }
}



