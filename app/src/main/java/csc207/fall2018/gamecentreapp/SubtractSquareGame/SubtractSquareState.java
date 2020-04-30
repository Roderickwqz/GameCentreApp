package csc207.fall2018.gamecentreapp.SubtractSquareGame;

import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * The state of the SubtractSquare game.
 */

public class SubtractSquareState implements Serializable {

    //    private static final int WIN = 1;
//
//    private static final int LOSE = -1;
//
//    private static final int DRAW = 0;


    /**
     * whether it is p1's turn.
     */
    private boolean p1Turn;

    private String p1Name;

    private String p2Name;

    /**
     * The current number after subtraction in the game.
     */
    private int currentTotal;


    // New Game State
    public SubtractSquareState(String p1Name, String p2Name) {
        this.p1Turn = true;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        // can be adjust by difficulty
        this.currentTotal = getRandomInt(200, 500);
    }


    private SubtractSquareState(boolean p1_turn, int current_total, String p1Name, String p2Name) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1Turn = p1_turn;
        this.currentTotal = current_total;
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    /**
     * Return whether it is p1's turn.
     *
     * @return boolean showing that it is p1's turn or not.
     */
    public boolean isP1_turn() {
        return this.p1Turn;
    }


    /**
     * Get all possible moves for a player.
     *
     * @return a list containing of all moves.
     */
    public ArrayList<Integer> getPossibleMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        int i = 1;
        while (i * i <= this.currentTotal) {
            moves.add(i * i);
            i++;
        }
        return moves;
    }

    /**
     * Get current_total
     *
     * @return an int representing current_total
     */
    public int getCurrentTotal() {
        return currentTotal;
    }

    /**
     * Return the new state after the player makes a move.
     *
     * @param move String representing the move.
     * @return a new SubtractSquareState.
     */
    public SubtractSquareState makeMove(String move) {
        int moveInt = Integer.parseInt(move);
        return new SubtractSquareState(!p1Turn, this.currentTotal - moveInt, this.p1Name, this.p2Name);
    }

//    /**
//     * Return the rough outcome.
//     *
//     * @return an int which shows win, draw or lose.
//     */
//    public int roughOutcome() {
//        if (checkSquare(this.current_total)) {
//            return WIN;
//        }
//        int i = 1;
//        while (i < Math.sqrt(current_total)) {
//            if (!checkSquare(current_total - i * i)) {
//                return DRAW;
//            }
//            i++;
//        }
//        return LOSE;
//    }


    private int getRandomInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random randInt = new Random();
        return randInt.nextInt((max - min) + 1) + min;
    }

}
