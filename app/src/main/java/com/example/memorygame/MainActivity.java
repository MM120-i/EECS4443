package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    public static int[] buttonIds = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
            R.id.button11, R.id.button12};
    private boolean isButtonPressInProgress = false;
    private boolean endGameButtonEnabled = true;

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
        highScoreTextView.setText("High Score: " + highScore);

        startButton = findViewById(R.id.startButton);
        endGameButton = findViewById(R.id.endGameButton);

        List<Button> buttons = new ArrayList<>();

        Game game = new Game(this);
        game.init(this);
        game.setRoundTextView(roundTextView);

        // Add all the buttons to the list
        for (int i = 1; i <= NUMBER_OF_BOXES; i++) {

            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            button.setOnClickListener(this);
            buttons.add(button);
        }

        startButton.setOnClickListener(this);
        endGameButton.setOnClickListener(this);
    }

    public Handler getHandler(){
        return handler;
    }

    public int getCurrentRound(){
        return round;
    }

    public void setButtonClickListener(ButtonClickListener listener){
        this.buttonClickListener = listener;
    }

    @Override
    public void onClick(View view) {

        if(isButtonPressInProgress){
            return;
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

        if (id == R.id.endGameButton) {
            if(endGameButtonEnabled){
                onEndGameButtonClick();
                endGameButtonEnabled = false;
            }
        }
        else if (id == R.id.startButton) {
            if(buttonClickListener != null){
                buttonClickListener.onStartButtonClick();
            }
        }

        // Change button colour when clicked.
        final Button clickedButton = findViewById(id);
        int clickedColour = ContextCompat.getColor(getApplicationContext(), R.color.clicked_button_color);
        changeButtonColor(clickedButton, clickedColour, false);
        handler.postDelayed(() -> changeButtonColor(clickedButton, Color.TRANSPARENT, true), DURATION);

        isButtonPressInProgress = false;
    }

    private void playTone(int toneType) {
        toneGenerator.startTone(toneType);
        handler.postDelayed(() -> toneGenerator.stopTone(), DURATION);
    }

    public void onEndGameButtonClick(){
        Intent gameOverIntent = new Intent(MainActivity.this, GameOverActivity.class);
        gameOverIntent.putExtra("round", round);
        startActivity(gameOverIntent);
    }

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



