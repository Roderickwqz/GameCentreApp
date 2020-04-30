package csc207.fall2018.gamecentreapp;

import android.content.Context;

/**
 * An interface dealing with the scores of games.
 */
public interface Score {

    String returnPlayerName();

    String returnGameName();

    String calculateScore();
}
