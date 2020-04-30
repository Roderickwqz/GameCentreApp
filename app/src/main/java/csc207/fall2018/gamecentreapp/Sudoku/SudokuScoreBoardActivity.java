package csc207.fall2018.gamecentreapp.Sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.ScoreDisplayable;

public class SudokuScoreBoardActivity extends AppCompatActivity implements ScoreDisplayable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_score_board);
        ListView listView = findViewById(R.id.scoreList);
        ArrayList<String> scores = loadGameScore(this, SudokuGenerator.getGameName());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, scores);
        listView.setAdapter(arrayAdapter);
    }

    public void onclickGoBack(View view) {
        Intent goBackIntent = new Intent(getApplicationContext(), SudokuGameStartingActivity.class);
        startActivity(goBackIntent);
    }
}
