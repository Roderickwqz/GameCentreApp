package csc207.fall2018.gamecentreapp.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.ScoreDisplayable;

public class MyScoreActivity extends AppCompatActivity implements ScoreDisplayable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

        ArrayList<String> scores= loadUserScore(this, BoardManager.getGameName());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, scores);
        ListView listView = findViewById(R.id.scoreList);
        listView.setAdapter(arrayAdapter);
    }

    public void onclickGoBack(View view) {
        Intent goBackIntent = new Intent(this, StartingActivity.class);
        startActivity(goBackIntent);
    }
}
