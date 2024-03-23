package com.example.memorygame;

import android.provider.BaseColumns;

/**
 * Contract class for the game results database.
 * Defines table and column names for the game results database.
 */
public class GameResultsContract {

    private GameResultsContract() {}

    /**
     * Inner class that defines the table contents.
     */
    public static class GameEntry implements BaseColumns {

        public static final String TABLE_NAME = "game_results";
        public static final String COLUMN_ACCURACY_RATE = "accuracy_rate";
        public static final String COLUMN_ERROR_RATE = "error_rate";
        public static final String COLUMN_ROUNDS = "rounds";
        public static final String COLUMN_AVERAGE_TIME_PER_ROUND = "average_time_per_round";
        public static final String COLUMN_COMPLETION_PERCENTAGE = "completion_percentage";

        // SQL statement to create the game results table.
        public static final String SQL_CREATE_TABLE =
                        "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_ACCURACY_RATE + " REAL," +
                        COLUMN_ERROR_RATE + " REAL," +
                        COLUMN_ROUNDS + " INTEGER," +
                        COLUMN_AVERAGE_TIME_PER_ROUND + " INTEGER," +
                        COLUMN_COMPLETION_PERCENTAGE + " INTEGER)";

    }
}

