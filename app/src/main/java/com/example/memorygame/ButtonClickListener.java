package com.example.memorygame;

/**
 * Interface for handling button click events in the Memory Game.
 * Provides methods to handle the clicks of the end game and start game buttons.
 */
public interface ButtonClickListener {

    /**
     * Called when the "End Game" button is clicked.
     */
    void onEndGameButtonClick();

    /**
     * Called when the "Start Game" button is clicked.
     */
    void onStartButtonClick();
}
