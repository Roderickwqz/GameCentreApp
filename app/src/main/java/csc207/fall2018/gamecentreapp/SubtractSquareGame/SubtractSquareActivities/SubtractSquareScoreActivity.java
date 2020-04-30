package csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;
import csc207.fall2018.gamecentreapp.ScoreDisplayable;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;

public class SubtractSquareScoreActivity extends AppCompatActivity implements ScoreDisplayable {

    ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_square_score);

        ListView listView = findViewById(R.id.scoreList);
        ArrayList<String> scores = loadGameScore(this, SubtractSquareGame.getGameName());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, scores);
        listView.setAdapter(arrayAdapter);
    }

    public void onclickGoBack(View view) {
        Intent goBackIntent = new Intent(getApplicationContext(), SubtractSquareGameCentreActivity.class);
        startActivity(goBackIntent);
    }
}
