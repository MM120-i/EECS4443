package com.example.memorygame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Utility class for logging the contents of a database table.
 */
public class DatabaseLogger {

    private static final String TAG = "DatabaseLogger";

    /**
     * Logs the contents of the specified database table.
     *
     * @param database The SQLiteDatabase object representing the database to log.
     * @param tableName The name of the table to log.
     */
    public static void logDatabaseContents(SQLiteDatabase database, String tableName) {

        // Check if the database and table name are valid
        if (database == null || tableName == null || tableName.isEmpty()) {
            Log.e(TAG, "Invalid database or table name");
            return;
        }

        // Query the database table to retrieve accuracy_rate, error_rate, and rounds
        Cursor cursor = database.rawQuery("SELECT accuracy_rate, error_rate, rounds, average_time_per_round, completion_percentage FROM " + tableName, null);


        // Check if the cursor is valid
        if (cursor == null) {
            Log.e(TAG, "Cursor is null");
            return;
        }

        // Log the column names
        Log.d(TAG, "Columns: accuracy_rate, error_rate, rounds");

        // Log the row data
        while (cursor.moveToNext()) {

            double accuracyRate = cursor.getDouble(cursor.getColumnIndexOrThrow("accuracy_rate"));
            double errorRate = cursor.getDouble(cursor.getColumnIndexOrThrow("error_rate"));
            int rounds = cursor.getInt(cursor.getColumnIndexOrThrow("rounds"));

            // Retrieve the average time per round from the cursor
            long averageTimePerRound = cursor.getLong(cursor.getColumnIndexOrThrow("average_time_per_round"));

            // Round the accuracy rate and error rate values
            DecimalFormat decimalFormat = new DecimalFormat("##.##");
            String roundedAccuracyRate = decimalFormat.format(accuracyRate);
            String roundedErrorRate = decimalFormat.format(errorRate);

            // Log the accuracy rate, error rate, rounds, average time, and completion percentage
            Log.d(TAG, "Accuracy Rate: " + roundedAccuracyRate + "%" +
                    ", Error Rate: " + roundedErrorRate + "%" +
                    ", Rounds: " + rounds +
                    ", Average Time per Round: " + averageTimePerRound + " s" +
                    ", Completion Percentage: " + 100 + "%");
        }

        // Close the cursor
        cursor.close();
    }
}

