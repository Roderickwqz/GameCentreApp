package csc207.fall2018.gamecentreapp;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;

public interface ScoreDisplayable {

    static ArrayList<String> getScoreList(Cursor c) {
        ArrayList<String> scores = new ArrayList<>();
        if (c.getCount() == 0) {
            scores.add("No score yet, play right now!");
        } else {
            int i = 1;
            int nameIndex = c.getColumnIndex("NAME");
            int gameIndex = c.getColumnIndex("GAME");
            int scoreIndex = c.getColumnIndex("SCORE");

            while (c.moveToNext()) {
                String temp = "No." + Integer.toString(i) + " |    "
                        + c.getString(gameIndex) + "  |  "
                        + c.getString(nameIndex) + "  |  "
                        + "Score: " + c.getString(scoreIndex);
                scores.add(temp);
                i++;
            }
        }
        return scores;
    }


    default ArrayList<String> loadUserScore(Context context, String gameName) {
        ScoreBoard scoreBoard = new ScoreBoard(context);
        Session session = Session.getInstance(context);

        Cursor c = scoreBoard.getScoreByGameAndName(session.getCurrentUserName(), gameName);
        return getScoreList(c);
    }


    default ArrayList<String> loadGameScore(Context context, String gameName) {
        ScoreBoard scoreBoard = new ScoreBoard(context);

        Cursor c = scoreBoard.getScoreByGame(gameName);
        return getScoreList(c);
    }


}
