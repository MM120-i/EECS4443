package com.example.memorygame;

/**
 * Interface for managing the high score in the Memory Game.
 * Provides methods to save and retrieve the high score using SharedPreferences.
 */
public interface ScoreManager {

    /**
     * The name of the SharedPreferences file used to store the high score.
     */
    String PREF_NAME = "MemoryGamePrefs";

    /**
     * The key used to store the high score in SharedPreferences.
     */
    String HIGH_SCORE_KEY = "highScore";

    /**
     * Saves the high score in SharedPreferences.
     *
     * @param score The high score to be saved.
     */
    void saveHighScore(int score);

    /**
     * Retrieves the high score from SharedPreferences.
     *
     * @return The current high score.
     */
    int getHighScore();
}
