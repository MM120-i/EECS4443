package com.example.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * HomeActivity represents the main screen of the Memory Game app where users can start the game.
 */
public class HomeActivity extends AppCompatActivity {

    private static final String ERROR = "ERROR";
    private boolean hasWindowFocus = false;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState Bundle containing the data supplied in {@link #onSaveInstanceState(Bundle)} if the activity is being re-initialized; otherwise null.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // The rules activity
        Button rulesButton = findViewById(R.id.rules_button);
        rulesButton.setOnClickListener(v -> openRulesActivity());

        // Settings button
        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Start fade-in animation on settings button
        settingsButton.startAnimation(fadeInAnimation);
        rulesButton.startAnimation(fadeInAnimation);

        // Begin button
        Button beginButton = findViewById(R.id.begin_button);

        if (beginButton != null) {

            // Start fade-in animation on begin button
            beginButton.startAnimation(fadeInAnimation);
            beginButton.setOnClickListener(view -> {

                try {
                    if (hasWindowFocus) {
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.i(ERROR, "Error occurred while navigating to MainActivity");
                }
            });
        }
        else {
            Log.i(ERROR, "Begin button not found in layout.");
        }
    }

    /**
     * Called when the current window of the activity gains or loses focus.
     *
     * @param hasFocus Indicates whether the window of this activity has focus.
     *                 true when the window gains focus, false otherwise.
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        super.onWindowFocusChanged(hasFocus);
        hasWindowFocus = hasFocus;
    }

    /**
     * Opens the RulesActivity to display the rules of the game.
     */
    private void openRulesActivity() {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
