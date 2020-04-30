package csc207.fall2018.gamecentreapp.Sudoku;

import csc207.fall2018.gamecentreapp.Score;

public class SudokuScore implements Score {

    private static final String GAME_NAME = "Sudoku";

    private int time;

    private String userName;

    private int size;


    public void takeInSizeTimeName(int size, int time, String userName) {
        this.time = time;
        this.size = size;
        this.userName = userName;
    }

    @Override
    public String returnPlayerName() {
        return userName;
    }

    @Override
    public String returnGameName() {
        return GAME_NAME;
    }

    @Override
    public String calculateScore() {
        float floatScore = time;
        int intScore;
        if (size == 3) {
            intScore = (int) (100 * (1 - (floatScore / (floatScore + 70))));
        } else if (size == 4) {
            intScore = (int) (100 * (1 - (floatScore / (floatScore + 150))));
        } else {
            intScore = (int) (100 * (1 - (floatScore / (floatScore + 200))));
        }
        return String.valueOf(intScore);
    }
}
