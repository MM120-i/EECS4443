package com.example.memorygame;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * SettingsActivity class responsible for handling the settings page of the app.
 * It allows users to perform various settings-related actions.
 */
public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "GamePrefs";
    private static final String KEY_MAX_ROUNDS = "maxRounds";
    public static final String KEY_VIBRATION_ENABLED = "vibration_enabled";

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find the clear database button
        Button clearDatabaseButton = findViewById(R.id.clear_database_button);
        clearDatabaseButton.setOnClickListener(v -> showConfirmationDialog());

        // Start the fade-in animation on the clear database button
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        clearDatabaseButton.startAnimation(fadeInAnimation);

        // Find the spinner and its associated text view
        Spinner roundsSpinner = findViewById(R.id.rounds_spinner);
        TextView roundsTextView = findViewById(R.id.rounds_text);
        Switch vibrationSwitch = findViewById(R.id.vibration_switch);

        // Start the fade-in animation on the spinner and its text view
        roundsSpinner.startAnimation(fadeInAnimation);
        roundsTextView.startAnimation(fadeInAnimation);

        // Set up the spinner for number of rounds
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rounds_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        roundsSpinner.setAdapter(adapter);
        roundsSpinner.setSelection(0);
        setupRoundsSpinner(roundsSpinner);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isVibrationEnabled = prefs.getBoolean(KEY_VIBRATION_ENABLED, true);

        vibrationSwitch.setChecked(isVibrationEnabled);
        vibrationSwitch = findViewById(R.id.vibration_switch);
        vibrationSwitch.setChecked(isVibrationEnabled());
        vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> saveVibrationPreference(isChecked));
        vibrationSwitch.startAnimation(fadeInAnimation); // Animation for the switch
    }

    /**
     * Retrieves the vibration preference from SharedPreferences.
     *
     * @return The vibration preference value retrieved from SharedPreferences.
     */
    private boolean isVibrationEnabled() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(KEY_VIBRATION_ENABLED, true); // Default value is true
    }

    /**
     * Saves the vibration preference to SharedPreferences.
     *
     * @param isEnabled The boolean value indicating whether vibration is enabled.
     */
    private void saveVibrationPreference(boolean isEnabled) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_VIBRATION_ENABLED, isEnabled);
        editor.apply();
    }

    /**
     * Sets up the spinner to handle the selection of the number of rounds.
     *
     * @param roundsSpinner The spinner for selecting the number of rounds.
     */
    private void setupRoundsSpinner(Spinner roundsSpinner) {

        // Load the selected number of rounds from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedRounds = prefs.getInt(KEY_MAX_ROUNDS, 3);

        roundsSpinner.setSelection(savedRounds - 3);

        roundsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int selectedRounds = position + 3;
                Game.MAX_ROUNDS = selectedRounds;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(KEY_MAX_ROUNDS, selectedRounds);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Game.MAX_ROUNDS = 3;
            }
        });
    }

    /**
     * Shows a confirmation dialog when the user clicks the clear database button.
     * This dialog asks the user to confirm if they want to clear the database.
     */
    private void showConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to clear the data?");
        builder.setPositiveButton("Yes", (dialog, which) -> clearDatabase());
        builder.setNegativeButton("No", null);
        builder.show();
    }

    /**
     * Clears the database.
     * This method is called when the user confirms the database clearing.
     */
    private void clearDatabase(){

        try(DatabaseHelper dbHelper = new DatabaseHelper(this)){
            dbHelper.clearDatabase();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Display a toast to indicate that the data was cleared successfully
        Toast.makeText(this, "Data cleared successfully", Toast.LENGTH_SHORT).show();
    }
}