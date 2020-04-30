package csc207.fall2018.gamecentreapp.slidingtiles;

import csc207.fall2018.gamecentreapp.Score;

public class SlidingTileScore implements Score {

    private static final String GAME_NAME = BoardManager.getGameName();

    private int time; //Seconds

    private String userName;

    private int size;

    private int numMove;


    public void takeInSizeTimeName(int size, int time, String userName, int numMove) {
        this.time = time;
        this.size = size;
        this.userName = userName;
        this.numMove = numMove;
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
        float floatMove = numMove - 1;
        float floatScore = time;
        int intScore;
        if (size == 3) {
            intScore = (int) (100 * (1 - ((floatScore + floatMove) / (floatScore + floatMove + 50))));
        } else if (size == 4) {
            intScore = (int) (100 * (1 - ((floatScore + floatMove) / (floatScore + floatMove + 100))));
        } else {
            intScore = (int) (100 * (1 - ((floatScore + floatMove) / (floatScore + floatMove + 200))));
        }
        return String.valueOf(intScore);
    }
}
