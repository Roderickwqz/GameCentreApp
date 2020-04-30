package csc207.fall2018.gamecentreapp;

import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareScore;
import csc207.fall2018.gamecentreapp.Sudoku.SudokuScore;
import csc207.fall2018.gamecentreapp.slidingtiles.BoardManager;
import csc207.fall2018.gamecentreapp.slidingtiles.SlidingTileScore;

public class ScoreFactory {

    private static final String SUBTRACT_SQUARE = SubtractSquareGame.getGameName();

    private static final String SLIDING_TILE = BoardManager.getGameName();

    //TODO: Need to Implement getGameName()
    private static final String SUDOKU = "Sudoku";

    public Score generateScore(String gameName) {
        if (gameName == null) {
            return null;
        } else if (gameName.equals(SUBTRACT_SQUARE)) {
            return new SubtractSquareScore();
        } else if (gameName.equals(SLIDING_TILE)) {
            return new SlidingTileScore();
        } else if (gameName.equals(SUDOKU)) {
            return new SudokuScore();
        }
        return null;
    }

}
