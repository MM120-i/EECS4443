package com.example.memorygame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Helper class for managing the SQLite database used in the game.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game.results.db";
    private static final int DATABASE_VERSION = 2;

    /**
     * Constructs a new DatabaseHelper instance.
     *
     * @param context The context to use.
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GameResultsContract.GameEntry.SQL_CREATE_TABLE);
        DatabaseLogger.logDatabaseContents(db, GameResultsContract.GameEntry.TABLE_NAME);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GameResultsContract.GameEntry.TABLE_NAME);
        onCreate(db);
        alterTableToAddAverageTimePerRoundColumn();
    }

    /**
     * Adds a new game result to the database.
     *
     * @param accuracyRate        The accuracy rate of the game result.
     * @param errorRate           The error rate of the game result.
     * @param rounds              The number of rounds completed.
     * @param averageTimePerRound The average time per round.
     */
    public void addGameResult(double accuracyRate, double errorRate, int rounds, long averageTimePerRound) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameResultsContract.GameEntry.COLUMN_ACCURACY_RATE, accuracyRate);
        values.put(GameResultsContract.GameEntry.COLUMN_ERROR_RATE, errorRate);
        values.put(GameResultsContract.GameEntry.COLUMN_ROUNDS, rounds);
        values.put(GameResultsContract.GameEntry.COLUMN_AVERAGE_TIME_PER_ROUND, averageTimePerRound);
        values.put(GameResultsContract.GameEntry.COLUMN_COMPLETION_PERCENTAGE, 100);

        try {
            db.insert(GameResultsContract.GameEntry.TABLE_NAME, null, values);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

    /**
     * Clears all data from the database.
     */
    void clearDatabase(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GameResultsContract.GameEntry.TABLE_NAME, null, null);
        db.close();
    }

    /**
     * Alters the database table to add the average time per round column.
     */
    public void alterTableToAddAverageTimePerRoundColumn() {

        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            // Execute the SQL command to add the new column
            db.execSQL("ALTER TABLE " + GameResultsContract.GameEntry.TABLE_NAME + " ADD COLUMN " + GameResultsContract.GameEntry.COLUMN_AVERAGE_TIME_PER_ROUND + " REAL");
            db.close();
        }
    }
}