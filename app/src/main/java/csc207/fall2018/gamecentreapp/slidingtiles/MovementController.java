package csc207.fall2018.gamecentreapp.slidingtiles;

import android.content.Context;
import android.widget.Toast;

import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;
import csc207.fall2018.gamecentreapp.ScoreFactory;
import csc207.fall2018.gamecentreapp.Session;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                updateScore(context);
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateScore(Context context) {
        ScoreBoard scoreBoard = new ScoreBoard(context);
        ScoreFactory factory = new ScoreFactory();
        SlidingTileScore score = (SlidingTileScore) factory.generateScore(BoardManager.getGameName());
        Session session = Session.getInstance(context);
        int numMove = boardManager.getNumPastMove();
        score.takeInSizeTimeName(boardManager.getBoard().NUM_COLS, boardManager.getIntTime(), session.getCurrentUserName(), numMove);
        scoreBoard.addScore(score);

    }
}
