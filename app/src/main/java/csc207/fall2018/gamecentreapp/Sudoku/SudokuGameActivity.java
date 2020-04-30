package csc207.fall2018.gamecentreapp.Sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.DataBase.GameStateDataBase;
import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;
import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.ScoreFactory;
import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;
import csc207.fall2018.gamecentreapp.Timer;

public class SudokuGameActivity extends AppCompatActivity {

    private AlertDialog.Builder enterNumber;
    private ArrayAdapter<Object> gridViewArrayAdapter;
    private ArrayList<Object> allValue;

    private EditText input;
    private ArrayList<Integer> okPosition;

    private Timer timer;
    private GridView gridOfSudoku;
    private SudokuGenerator sudokuGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_game_interface);

        loadFromFile(SudokuGameStartingActivity.FILE_NAME);
        okPosition = sudokuGenerator.getOkPosition();

        gridOfSudoku = findViewById(R.id.sudokuGrid);

        timer = new Timer(findViewById(R.id.timer));
        timer.setUpTimer(sudokuGenerator.getIntTime());
        timer.start();
        input = new EditText(this);

        Button undoButton = findViewById(R.id.undo);
        undoButton.setEnabled(false);

        enterNumber = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Type in new number")
                .setView(input)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getIntent().getExtras().getInt("position");
                        if (checkInputValidity(input.getText().toString())) {
                            int newValue = Integer.parseInt(input.getText().toString());
                            enteredValue(position, newValue);
                            Button undoButton = findViewById(R.id.undo);
                            undoButton.setEnabled(true);
                            gridViewArrayAdapter.notifyDataSetChanged();
                            saveToDataBase();
                        } else {
                            Toast.makeText(SudokuGameActivity.this, "Please enter a valid number",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        generateGrid();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void generateGrid() {
        ArrayList<ArrayList<Integer>> sudoku = sudokuGenerator.getFinalSudoku();
        allValue = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            int x = i % 9;
            int y = i / 9;
            if (sudoku.get(x).get(y) == 0) {

                allValue.add("");
            } else {
                allValue.add(sudoku.get(x).get(y));
            }

        }
        gridViewArrayAdapter = new ArrayAdapter<Object>
                (this, android.R.layout.simple_list_item_1, allValue) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                int color = Color.parseColor("#DB7093");
                if (okPosition.contains(position)) {
                    view.setBackgroundColor(color);
                }
                return view;
            }
        };
        gridOfSudoku.setAdapter(gridViewArrayAdapter);
        gridOfSudoku.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (okPosition.contains(position)) {
                            //Restate EditText input because the view is added to dialog layout before
                            //so it has a parent layout right now, must remove it or restate a new view.
                            getIntent().putExtra("position", position);
                            input = new EditText(SudokuGameActivity.this);
                            enterNumber.setView(input);
                            sudokuGenerator.trackMoves(position);
                            enterNumber.show();
                        }
                    }
                }
        );

    }

    public void undo(View v) {
        Button undoButton = (Button) v;
        Animation bounce = AnimationUtils.loadAnimation(SudokuGameActivity.this, R.anim.bounce);
        undoButton.setAnimation(bounce);
        int numMoves = sudokuGenerator.getMoves().size();
        if (numMoves != 0) {
            int lastMove = sudokuGenerator.getMoves().remove(numMoves - 1);
            if (sudokuGenerator.getMoves().size() == 0) {
                undoButton.setEnabled(false);
            }
            allValue.set(lastMove, "");
            gridViewArrayAdapter.notifyDataSetChanged();
        } else {
            undoButton.setEnabled(false);
        }
    }

    public void startAgain(View v) {
        Button restart = (Button) v;
        Animation bounce = AnimationUtils.loadAnimation(SudokuGameActivity.this, R.anim.bounce);
        restart.setAnimation(bounce);
//        sudokuGenerator = new SudokuGenerator(sudokuGenerator.getFinalSudoku().size());
        //transit();
        sudokuGenerator = new SudokuGenerator(2);
        okPosition = sudokuGenerator.getOkPosition();
        generateGrid();

    }

    public void endGameCheck(View v) {
        ArrayList<ArrayList<Integer>> sudoku = sudokuGenerator.getFinalSudoku();
        int counter = 0;
        int currentPosition = 0;
        while (currentPosition < 81) {
            int row = currentPosition % 9;
            int col = currentPosition / 9;
            if (sudokuGenerator.checkAll(sudoku.get(row).get(col), row, col, sudoku)) {
                counter++;
            }
            currentPosition++;
        }
        if (counter == 81) {
            timer.stop();
            updateScore();
            Toast.makeText(SudokuGameActivity.this, "Congratulation", Toast.LENGTH_LONG).show();
            //------------Have timer stop and score logged at here---------------------
            //-------------------------------------------------------------------------
        } else {
            Toast.makeText(SudokuGameActivity.this, "Failed",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void enteredValue(int position, int newValue) {
        allValue.set(position, newValue);
        sudokuGenerator.changeValue(position, newValue);
    }

    public boolean checkInputValidity(String input) {
        boolean a = input.length() == 1;
        return a && input.matches("-?([1-9]\\d*)");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
    }

    private void transit() {
        sudokuGenerator = new SudokuGenerator(3);
        okPosition = sudokuGenerator.getOkPosition();
        generateGrid();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        timeAnimation.start();
//    }

    private void loadFromFile(String fileName) {
        try {
            File inputFile = new File(getFilesDir(), fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(inputFile));
            sudokuGenerator = (SudokuGenerator) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }


    public void onclickGoBack(View view) {
        saveToDataBase();
        Intent intent = new Intent(this, SudokuGameStartingActivity.class);
        startActivity(intent);
    }

    private void saveToDataBase() {
        sudokuGenerator.setTime(timer.returnStringTime());
        GameStateDataBase dataBase = new GameStateDataBase(this);
        byte[] stream = null;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(sudokuGenerator);
            stream = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Session session = Session.getInstance(this);
        dataBase.deleteState(session.getCurrentUserName(), SudokuGenerator.getGameName());
        dataBase.saveState(session.getCurrentUserName(), SudokuGenerator.getGameName(), stream);
    }

    private void updateScore() {
        ScoreBoard scoreBoard = new ScoreBoard(this);
        ScoreFactory factory = new ScoreFactory();
        SudokuScore score = (SudokuScore) factory.generateScore(SudokuGenerator.getGameName());
        Session session = Session.getInstance(this);
        sudokuGenerator.setTime(timer.returnStringTime());
        score.takeInSizeTimeName(sudokuGenerator.getFinalSudoku().size(), sudokuGenerator.getIntTime(), session.getCurrentUserName());
        scoreBoard.addScore(score);
    }
}


