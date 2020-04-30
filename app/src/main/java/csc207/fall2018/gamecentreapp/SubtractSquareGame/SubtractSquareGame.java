package csc207.fall2018.gamecentreapp.SubtractSquareGame;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import csc207.fall2018.gamecentreapp.TimeStorable;

/**
 * subtract square game manager
 */
public class SubtractSquareGame implements Serializable, Iterable<SubtractSquareState>, TimeStorable {

    /**
     * current state of the game.
     */
    private static final String GAME_NAME = "Subtract Square";

    private SubtractSquareState currentState;

    private int undoBatch;

    private String time = "00:00";


//    private String p1Name;
//
//    private String p2Name;

    private ArrayList<SubtractSquareState> pastStates;


//    /**
//     * initialize a game
//     */
//    private SubtractSquareGame(String p1Name, String p2Name, int count) {
//        SubtractSquareGame.p1Name = p1Name;
//        SubtractSquareGame.p2Name = p2Name;
//        this.currentState = new SubtractSquareState(true, count);
//    }

    public SubtractSquareGame(String p1Name, String p2Name) {
        this.undoBatch = 3;
        this.currentState = new SubtractSquareState(p1Name, p2Name);
        this.pastStates = new ArrayList<>();
    }

//    public String getP1Name() {
//        return p1Name;
//    }
//
//    public String getP2Name() {
//        return p2Name;
//    }

    public static String getGameName() {
        return GAME_NAME;
    }

    public String getCurrentPlayerName() {
        return currentState.isP1_turn() ? currentState.getP1Name() : currentState.getP2Name();
    }

    public SubtractSquareState getCurrentState() {
        return currentState;
    }

    public int getUndoBatch() {
        return undoBatch;
    }

    public void setUndoBatch(int undoBatch) {
        this.undoBatch = undoBatch;
    }

    public void applyMove(String move) {
        try {
            this.pastStates.add(0, this.currentState);
            this.currentState = currentState.makeMove(move);
        } catch (NumberFormatException e) {
            Log.e("Exception", "Parse failed: " + e.toString());
        }
    }

    public boolean undoMove() {
        Iterator<SubtractSquareState> stateIterator = pastStates.iterator();
        boolean undoable = stateIterator.hasNext();
        if (undoable) {
            this.currentState = stateIterator.next();
            deleteState();
        }
        return undoable;
    }


    public boolean is_over() {
        return currentState.getCurrentTotal() == 0;
    }


    public String getWinner() {
        if (is_over()) {
            return (currentState.isP1_turn()) ? currentState.getP2Name() : currentState.getP1Name();
        }
        return "Not finished";
    }


    public boolean isValidMove(String move) {
        if (isInteger(move)) {
            int moveInt = Integer.parseInt(move);
            return (moveInt <= this.currentState.getCurrentTotal()) && (moveInt > 0) && (checkSquare(moveInt));
        }
        return false;
    }

    public boolean isInteger(String str) {
        return str.matches("-?(0|[1-9]\\d*)");
    }

    /**
     * helper function for roughOutcome which checks whether a number is a square number.
     *
     * @param n int which is checked.
     * @return a boolean whether n is a square number.
     */
    public boolean checkSquare(int n) {
        for (int i = 1; i <= n; i++) {
            if (i * i == n) return true;
        }
        return false;
    }

    private SubtractSquareState getState(int index) {
        return this.pastStates.get(index);
    }

    private void deleteState() {
        this.pastStates.remove(0);
    }

    private int numState() {
        return this.pastStates.size();
    }

    @NonNull
    @Override
    public Iterator<SubtractSquareState> iterator() {
        return new SubtractSquareStateIterator();
    }

    private class SubtractSquareStateIterator implements Iterator<SubtractSquareState> {

        int index = 0;


        @Override
        public boolean hasNext() {
            return index != numState();
        }

        @Override
        public SubtractSquareState next() {
            SubtractSquareState result = getState(index);
            index++;
            return result;
        }


    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getIntTime() {
        time = time.replace(":", "");
        return Integer.valueOf(time);
    }
}

