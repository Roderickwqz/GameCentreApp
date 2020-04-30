package csc207.fall2018.gamecentreapp.Sudoku;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import csc207.fall2018.gamecentreapp.TimeStorable;

class SudokuGenerator implements Serializable, TimeStorable {

    private static final String GAME_NAME = "Sudoku";
    private Random rand = new Random();

    private String time = "00:00";
    private ArrayList<ArrayList<Integer>> finalSudoku;
    private ArrayList<Integer> moves = new ArrayList();
    private ArrayList<Integer> okPosition = new ArrayList<>();

    SudokuGenerator(int level) {
        finalSudoku = constructor(level);
        setOkPosition();
    }

    //    static SudokuGenerator getInstance(int level){
    private ArrayList<ArrayList<Integer>> constructor(int level) {

        ArrayList<ArrayList<Integer>> grid = createBlankSudoku();
        ArrayList<ArrayList<Integer>> available = createAvailable();


        int pos = 0;
        while (pos < 81) {
            int row = pos / 9;
            int col = pos % 9;

            if (available.get(pos).size() != 0) {

                int i = rand.nextInt(available.get(pos).size());
                int number = available.get(pos).get(i);

                if (checkAll(number, row, col, grid)) {
                    grid.get(row).set(col, number);
                    available.get(pos).remove(i);
                    pos++;
                } else {
                    available.get(pos).remove(i);
                }
            } else {
                grid.get(row).set(col, 0);
                available.set(pos, oneToNine());
                pos--;
            }
        }
        generateHoles(grid, level);
        return grid;
    }


    /**
     * Erase an existing value in some tiles in the sudoku board so that it can take in user input.
     * The number of empty tiles is decided by the difficulty of the sudoku game.
     *
     * @param  grid the sudoku board to operate on
     * @param level the difficulty level of the sudoku game
     */
    private void generateHoles(ArrayList<ArrayList<Integer>> grid, int level) {
        int numHoles = level * 9;
        ArrayList<Integer> posList = new ArrayList<>();
        for (int i = 1; i < 81; i++) {
            posList.add(i);
        }
        Collections.shuffle(posList);
        for (int j = 0; j < numHoles; j++) {
            int row = posList.get(j) / 9;
            int col = posList.get(j) % 9;
            grid.get(row).set(col, 0);
        }
    }

    public static String getGameName() {
        return GAME_NAME;
    }


    private void setOkPosition() {
        for (int i = 0; i < 81; i++) {
            int x = i % 9;
            int y = i / 9;
            if (finalSudoku.get(x).get(y) == 0) {
                okPosition.add(i);
            }
        }
    }

    public ArrayList<Integer> getOkPosition() {
        return okPosition;
    }

    ArrayList<ArrayList<Integer>> getFinalSudoku() {
        return finalSudoku;
    }

    /**
     * Change the value at the given position
     *
     * @param position the position to change the value
     * @param value the new value at the position
     */
    void changeValue(int position, int value) {
        int x = position % 9;
        int y = position / 9;
        finalSudoku.get(x).set(y, value);
    }

    /**
     * Change the value at the given position
     *
     * @return  a 9 x 9 two dimensional ArrayList full of 0, where 0 represent an empty sudoku tile.
     */
    private ArrayList<ArrayList<Integer>> createBlankSudoku() {
        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            grid.add(i, new ArrayList<Integer>());
            for (int j = 0; j <= 8; j++) {
                //add check horizontal, vertical, and square method
                //if all conditions satisfies, then add this value.
                grid.get(i).add(0);
            }
        }
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder ai = new StringBuilder();
        int pos = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j <= 8; j++) {
                ai.append(finalSudoku.get(i).get(j) + " ");
            }
            ai.append("\n");
        }
        return ai.toString();
    }

//    public  String print(){
//        return finalSudoku.toString();
//    }


    /**
     * Add integer one to nine to an ArrayList
     *
     */
    private ArrayList<Integer> oneToNine() {
        return new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
            add(7);
            add(8);
            add(9);
        }};
    }

    /**
     * Add one to nine to each position of the sudoku board.
     *
     * @return  a two dimensional ArrayList of length 81, Each item is an ArrayList consisting of
     * integer oe to nine.
     */
    private ArrayList<ArrayList<Integer>> createAvailable() {
        ArrayList<ArrayList<Integer>> available = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            available.add(i, oneToNine());
        }
        return available;
    }

    /**
     * Check whether the value already exist in the given row , excluding the given column
     *
     * @param value the value at given row and column
     * @param row the row the value at
     * @param col the column the value at
     * @param grid the current sudoku board
     * @return  whether the value already exist in the row.
     */
    private boolean checkHorizontal(int value, int row, int col, ArrayList<ArrayList<Integer>> grid) {
        for (int i = 0; i < 9; i++) {
            if (value == grid.get(row).get(i) && i != col) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the value already exist in the given column, excluding the given row
     *
     * @param value the value at the given row and column
     * @param row the row the value at
     * @param col the column the value at
     * @param grid the current sudoku board
     * @return  whether the value already exist in the column.
     */
    private boolean checkVertical(int value, int row, int col, ArrayList<ArrayList<Integer>> grid) {
        for (int i = 0; i < 9; i++) {
            if (value == grid.get(i).get(col) && i != row) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the input value already exist in the 3 x 3 square the given row and column at,
     * excluding the position of the given row and column
     *
     * @param value the input value
     * @param row the row the value at
     * @param col the column the value at
     * @param grid the current sudoku board
     * @return  whether the value already exist in the 3 x 3 square.
     */
    private boolean checkSquare(int value, int row, int col, ArrayList<ArrayList<Integer>> grid) {
        int xGroup = (col / 3);
        int yGroup = (row / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (value == grid.get(yGroup * 3 + i).get(xGroup * 3 + j) && (row != (yGroup * 3 + i) || col != (xGroup * 3 + j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check whether the value at the given row and column cause a conflict according to the sudoku
     * rule
     *
     * @param value the value at the given row and column
     * @param row the row the value at
     * @param col the column the value at
     * @param grid the current sudoku board
     * @return  whether the value at the given row and column cause a conflict
     */
    boolean checkAll(int value, int row, int col, ArrayList<ArrayList<Integer>> grid) {
        return (checkHorizontal(value, row, col, grid)
                && checkVertical(value, row, col, grid)
                && checkSquare(value, row, col, grid));
    }

    /**
     * Add the  the position of the moves the user have made to track moves. If the position of
     * the latest move the user make is already in the moves, delete all previous occurrence.
     *
     * @param pos the position the user add inout
     */
    void trackMoves(int pos) {
        if (!moves.contains(pos)){
            moves.add(pos);
        }else{
            int numMoves = moves.size();
            for (int i=0; i <numMoves; i++){
                if (moves.get(i) == pos){
                    moves.remove(i);
                    i--;
                    numMoves = moves.size();
                }
            }
            moves.add(pos);
        }
    }

    /**
     * Get the moves the user have made
     *
     * @return the moves the user have made
     */
    ArrayList<Integer> getMoves() {
        return moves;
    }


    @Override
    public int getIntTime() {
        time = time.replace(":", "");
        return Integer.valueOf(time);
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }
}




