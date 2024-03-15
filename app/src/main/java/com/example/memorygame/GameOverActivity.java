package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.memorygame.MainActivity.round;
import static com.example.memorygame.MainActivity.highScore;

/**
 * This activity represents the game over screen, displayed to the user when the game ends.
 */
public class GameOverActivity extends AppCompatActivity{

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

        // Retrieve high score from intent extras and display it in the TextView
        int highScore =  getIntent().getIntExtra("high score", 0);
        TextView scoreTextView = findViewById(R.id.highScoreTextView);
        scoreTextView.setText("High Score: " + highScore);

        // Retrieve round from intent extras and display it in the TextView
        int round = getIntent().getIntExtra("round", 0);
        TextView roundTextView = findViewById(R.id.roundTextView);
        roundTextView.setText("Round: " + round);

        // Set up a click listener for the restartButton to restart the game
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> restartGame());
    }

    /**
     * This method restarts the game by resetting the round and high score to zero, then creating a new
     * intent to launch the MainActivity.
     */
    private void restartGame() {

        round = 0;
        highScore = 0;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("round", round);
        intent.putExtra("high score", highScore);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finish the current activity to remove it from the stack
        finish();
    }

}
