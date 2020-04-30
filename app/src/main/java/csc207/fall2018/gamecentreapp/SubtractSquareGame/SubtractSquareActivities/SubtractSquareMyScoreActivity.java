package csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.ScoreDisplayable;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;

public class SubtractSquareMyScoreActivity extends AppCompatActivity implements ScoreDisplayable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_square_my_score);

        ArrayList<String> scores= loadUserScore(this, SubtractSquareGame.getGameName());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, scores);
        ListView listView = findViewById(R.id.scoreList);
        listView.setAdapter(arrayAdapter);
    }

//    private ArrayList<String> loadScoreForDisplay(Cursor c) {
//        ArrayList<String> scores = new ArrayList<>();
//        if (c.getCount() == 0) {
//            scores.add("No score yet, play right now!");
//        } else {
////            c.moveToFirst();
//            int i = 1;
//            int nameIndex = c.getColumnIndex("NAME");
//            int gameIndex = c.getColumnIndex("GAME");
//            int scoreIndex = c.getColumnIndex("SCORE");
//
//            while (c.moveToNext()) {
//                String temp = "No." + Integer.toString(i) + " |    "
//                        + c.getString(gameIndex) + "  |  "
//                        + c.getString(nameIndex) + "  |  "
//                        + "Score: " + c.getString(scoreIndex);
//                scores.add(temp);
//                i++;
//            }
//        }
//        return scores;
//    }

    public void onclickGoBack(View view) {
        Intent goBackIntent = new Intent(this, SubtractSquareGameCentreActivity.class);
        startActivity(goBackIntent);
    }
}
