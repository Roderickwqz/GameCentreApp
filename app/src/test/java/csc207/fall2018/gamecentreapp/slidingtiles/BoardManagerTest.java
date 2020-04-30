package csc207.fall2018.gamecentreapp.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardManagerTest {

    BoardManager boardManager;


    private List<Tile> makeTiles(int size) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = size * size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    private void setUpCorrect(int size) {
        List<Tile> tiles = makeTiles(size);
        Board board = new Board(tiles, size);
        boardManager = new BoardManager(board);
    }

    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }


    @Test
    public void getBoard() {

    }

    @Test
    public void getGameName() {

    }

    @Test
    public void puzzleSolved() {
        setUpCorrect(5);
        assertEquals(true, boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.puzzleSolved());
    }

    @Test
    public void isValidTap() {
    }

    @Test
    public void touchMove() {
    }

    @Test
    public void undoMove() {
    }

    @Test
    public void iterator() {
    }
}