package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.memorygame.MainActivity.round;

public class GameOverActivity extends AppCompatActivity{

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int round = getIntent().getIntExtra("round", 0);
        TextView roundTextView = findViewById(R.id.roundTextView);
        roundTextView.setText("Round: " + round);

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> restartGame());
    }

    private void restartGame() {

        round = 0;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("round", round);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finish the current activity to remove it from the stack
        finish();
    }

}
