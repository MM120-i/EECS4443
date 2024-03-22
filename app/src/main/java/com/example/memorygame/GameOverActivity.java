package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.memorygame.Game.accuracyRate;
import static com.example.memorygame.Game.errorRate;
import static com.example.memorygame.MainActivity.round;
import static com.example.memorygame.MainActivity.highScore;
import static com.example.memorygame.Game.MAX_ROUNDS;
import static com.example.memorygame.Game.totalTime;


import java.text.DecimalFormat;


/**
 * This activity represents the game over screen, displayed to the user when the game ends.
 */
public class GameOverActivity extends AppCompatActivity implements ScoreManager{

    /**
     * This method is called when the activity is first created. It initializes the layout and retrieves
     * the high score and round information from the intent extras. Then, it sets the text of the
     * corresponding TextViews to display the high score and round.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state, or null if
     *                           there is no saved state
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int prevHighScore = getHighScore();

        if(highScore > prevHighScore){
            saveHighScore(highScore);
        }

        TextView scoreTextView = findViewById(R.id.highScoreTextView);
        scoreTextView.setText("High Score: " + getHighScore());

        // Retrieve round from intent extras and display it in the TextView
        int round = getIntent().getIntExtra("round", 0);
        TextView roundTextView = findViewById(R.id.roundTextView);
        roundTextView.setText("Round: " + round);

        long averageTimePerRoundMillis = getIntent().getLongExtra("average_time_per_round", 0);
        long averageTimePerRoundSecs = averageTimePerRoundMillis / 1000; // Convert milliseconds to seconds

        if(round == MAX_ROUNDS){
            TextView averageTimeTextView = findViewById(R.id.averageTimeTextView);
            averageTimeTextView.setText("Average Time per round: " + averageTimePerRoundSecs + " s");
        }
        else{
            TextView averageTimeTextView = findViewById(R.id.averageTimeTextView);
            averageTimeTextView.setText("Game not completed");
        }

        // Calculate completion percentage based on the number of rounds completed
        int completionPercentage = (round * 100) / MAX_ROUNDS;
        TextView completionPercentageTextView = findViewById(R.id.completionPercentageTextView);
        completionPercentageTextView.setText("Completion: " + completionPercentage + "%");

        TextView accuracyRateTextView = findViewById(R.id.accuracy);
        accuracyRateTextView.setText("Accuracy Rate: " + new DecimalFormat("##.##").format(accuracyRate) + "%");

        TextView errorRateTextView = findViewById(R.id.errorRate);
        errorRateTextView.setText("Error Rate: " + new DecimalFormat("##.##").format(errorRate) + "%");

        // Set up a click listener for the restartButton to restart the game
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> restartGame());
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
    public int getHighScore() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(HIGH_SCORE_KEY, 0);
    }

    /**
     * This method restarts the game by resetting the round and total time, then creating a new
     * intent to launch the MainActivity.
     */
    @SuppressLint("SetTextI18n")
    private void restartGame() {

        round = 0;
        totalTime = 0;
        accuracyRate = 0.0;
        errorRate = 0.0;

        Game.userInputList.clear();
        TextView completionPercentageTextView = findViewById(R.id.completionPercentageTextView);
        completionPercentageTextView.setText("Completion: 0%");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("round", round);
        intent.putExtra("high score", highScore);
        intent.putExtra("accuracy_rate", accuracyRate);
        intent.putExtra("error_rate", errorRate);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finish the current activity to remove it from the stack
        finish();
    }
}