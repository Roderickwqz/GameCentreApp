package csc207.fall2018.gamecentreapp.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import csc207.fall2018.gamecentreapp.TimeStorable;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable, Iterable<Integer>, TimeStorable {

    /**
     * The board being managed.
     */
    private Board board;

    private static final String GAME_NAME = "Sliding Tiles";

    private ArrayList<Integer> pastMove = new ArrayList<>();

    private String time = "00:00";

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     *
     * @return the current board
     */
    Board getBoard() {
        return board;
    }

//    /**
//     * Manage a new shuffled board.
//     */
//    BoardManager() {
//        List<Tile> tiles = new ArrayList<>();
//        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
//        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//            tiles.add(new Tile(tileNum));
//        }
//        Collections.shuffle(tiles);
//        this.board = new Board(tiles);
//    }


    // TODO: hard coded, need to change later
    BoardManager(int size) {
//        this.size = size;
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = size * size;
        ArrayList<Integer> tmpSize = new ArrayList<>();
        tmpSize.add(size);
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, tmpSize));
        }
//        Collections.shuffle(tiles);
        shuffle(tiles);
//        ArrayList<Integer> tmpSize = new ArrayList<>();
//        tmpSize.add(size);
        this.board = new Board(tiles, size);
    }


    //TODO: complete shuffle for a solvable game
    private void shuffle(List<Tile> tileList) {
        int i = 0;
        while (i != 200) {
            shuffleOnetime(tileList);
            i++;
        }
    }

    private void shuffleOnetime(List<Tile> tileList) {
        int numTile = tileList.size();
        int blankIndex = findBlankTile(tileList);
        Tile blankTile = tileList.get(blankIndex);
        int size = (int) Math.sqrt(numTile);
        int rowIndex = blankIndex / size;
        int colIndex = blankIndex % size;
        List<Tile> possible = new ArrayList<>();
        if (rowIndex != 0) {
            possible.add(tileList.get(blankIndex - size));
        }
        if (rowIndex != size - 1) {
            possible.add(tileList.get(blankIndex + size));
        }
        if (colIndex != 0) {
            possible.add(tileList.get(blankIndex - 1));
        }
        if (colIndex != size - 1) {
            possible.add(tileList.get(blankIndex + 1));
        }
        Tile random = possible.get(new Random().nextInt(possible.size()));
        int position = tileList.indexOf(random);
        tileList.set(position, blankTile);
        tileList.set(blankIndex, random);
    }

    private int findBlankTile(List<Tile> tileList) {
        for (int i = 0; i < tileList.size(); i++) {
            if (tileList.get(i).getId() == tileList.size()) {
                return i;
            }
        }
        return -1;
    }


    public static String getGameName() {
        return GAME_NAME;
    }

    public int getNumPastMove() {
        return pastMove.size();
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> currentBoard = getBoard().iterator();
        int curId = currentBoard.next().getId();
        while (currentBoard.hasNext()) {
            int nextId = currentBoard.next().getId();
            solved = solved && (nextId - curId == 1);
            curId = nextId;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / board.NUM_ROWS;
        int col = position % board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Return the position of the blank tile in the current board.
     *
     * @return the position of the blank tile in the current board
     */
    private int findBlankTile() {
        int position = 0;
        while (board.getTile(position / board.NUM_COLS, position %
                board.NUM_COLS).getId() != board.numTiles()) {
            position++;
        }
        return position;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        this.pastMove.add(0, findBlankTile());
        this.pastMove.add(0, position);
        int row = position / board.NUM_COLS;    // fixed, not Board.NUM_ROWS
        int col = position % board.NUM_COLS;
        if (isValidTap(position)) {
            int index = findBlankTile();
            board.swapTiles(row, col, index / board.NUM_COLS, index % board.NUM_COLS);
        }
    }

    boolean UndoMove() {
        Iterator<Integer> moveIterator = pastMove.iterator();
        boolean undoable = moveIterator.hasNext();
        if (undoable) {
            int firstPosition = moveIterator.next();
            int secondPosition = moveIterator.next();
            pastMove.remove(0);
            pastMove.remove(0);
            board.swapTiles(firstPosition / board.NUM_COLS,
                    firstPosition % board.NUM_COLS,
                    secondPosition / board.NUM_COLS,
                    secondPosition % board.NUM_COLS);
        }
        return undoable;
    }

    @NonNull
    @Override
    public Iterator<Integer> iterator() {
        return new MoveIterator();
    }

    private class MoveIterator implements Iterator<Integer> {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index != pastMove.size();
        }

        @Override
        public Integer next() {
            return pastMove.get(index);
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
