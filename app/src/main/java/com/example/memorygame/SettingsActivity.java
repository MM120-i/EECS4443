package com.example.memorygame;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * SettingsActivity class responsible for handling the settings page of the app.
 * It allows users to perform various settings-related actions.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
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

        Toast.makeText(this, "Data cleared successfully", Toast.LENGTH_SHORT).show();

    }
}
