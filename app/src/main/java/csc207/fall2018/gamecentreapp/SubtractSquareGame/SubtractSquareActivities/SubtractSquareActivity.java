package csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import csc207.fall2018.gamecentreapp.DataBase.GameStateDataBase;
import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;
import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.ScoreFactory;
import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.Dialogs.UndoPaymentDialog;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.MiniMaxNode;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareScore;
import csc207.fall2018.gamecentreapp.Timer;
import csc207.fall2018.gamecentreapp.slidingtiles.BoardManager;
import csc207.fall2018.gamecentreapp.slidingtiles.SlidingTileScore;

public class SubtractSquareActivity extends AppCompatActivity {

    private final static String TEMP_FILE_NAME = "temp_file.ser";

    private SubtractSquareGame subtractSquareGame;

    private boolean pcModel;

    private Timer timer;


//    private int undoBatch = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_square);
        loadFromFile(TEMP_FILE_NAME);

        pcModel = getIntent().getExtras().getBoolean("PC_MODE");

        timer = new Timer(findViewById(R.id.chronometer2));
        timer.setUpTimer(subtractSquareGame.getIntTime());
        timer.start();

        updateUndoTimes();
        updateCurrentTotal();
        updateProgressContext();
        saveToDataBase();
    }

    public void onclickGoBack(View view) {
        saveToDataBase();
        Intent goBackIntent = new Intent(getApplicationContext(), SubtractSquareGameCentreActivity.class);
        startActivity(goBackIntent);
    }

    //TODO: HARD CODE !!
    public void onclickEnter(View view) {
        EditText input = findViewById(R.id.input);

        if (!pcModel) {
            String move = input.getText().toString();
            if (subtractSquareGame.isValidMove(move)) {
                subtractSquareGame.applyMove(move);
                updateCurrentTotal();
                updateProgressContext();
                saveToFile(TEMP_FILE_NAME);
                input.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Not a valid number", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (subtractSquareGame.getCurrentState().isP1_turn()) {
                String move = input.getText().toString();
                if (subtractSquareGame.isValidMove(move)) {
                    subtractSquareGame.applyMove(move);
                    updateCurrentTotal();
                    updateProgressContext();
                    saveToFile(TEMP_FILE_NAME);
                    // PC CHOICE
                    MiniMaxNode pcChoice = new MiniMaxNode();
                    int choice = pcChoice.iterativeMiniMax(subtractSquareGame);
                    input.setText(String.valueOf(choice));
                } else {
                    Toast.makeText(getApplicationContext(), "Not a valid number", Toast.LENGTH_SHORT).show();
                }
            } else {
                String move = input.getText().toString();
                if (subtractSquareGame.isValidMove(move)) {
                    subtractSquareGame.applyMove(move);
                    updateCurrentTotal();
                    updateProgressContext();
                    saveToFile(TEMP_FILE_NAME);
                    input.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Not a valid number", Toast.LENGTH_SHORT).show();
                    MiniMaxNode pcChoice = new MiniMaxNode();
                    int choice = pcChoice.iterativeMiniMax(subtractSquareGame);
                    input.setText(String.valueOf(choice));
                }
            }
        }
        saveToDataBase();
    }


    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            File inputFile = new File(getFilesDir(), fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(inputFile));
            subtractSquareGame = (SubtractSquareGame) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the subtract square to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            File outputFile = new File(getFilesDir(), fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile));
            objectOutputStream.writeObject(subtractSquareGame);
            objectOutputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void saveToDataBase() {
        subtractSquareGame.setTime(timer.returnStringTime());
        GameStateDataBase dataBase = new GameStateDataBase(this);
        byte[] stream = null;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(subtractSquareGame);
            stream = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Session session = Session.getInstance(this);
        dataBase.deleteState(session.getCurrentUserName(), SubtractSquareGame.getGameName());
        dataBase.saveState(session.getCurrentUserName(), SubtractSquareGame.getGameName(), stream);
    }

    private void updateProgressContext() {
        TextView progress = (TextView) findViewById(R.id.progress);
        String progressContext;
        if (subtractSquareGame.is_over()) {
            timer.stop();
            updateScore(this);
            progressContext = "Game over, " + subtractSquareGame.getWinner() + " win !";
//            chronometer.stop();
        } else {
            if (pcModel && !(subtractSquareGame.getCurrentState().isP1_turn())) {
                progressContext = subtractSquareGame.getCurrentPlayerName() + " has made choice";
            } else {
                progressContext = subtractSquareGame.getCurrentPlayerName() + "'s turn";
            }
        }
        progress.setText(progressContext);
    }

    private void updateCurrentTotal() {
        TextView gameNumber = (TextView) findViewById(R.id.gameNumber);
        int currentTotal = subtractSquareGame.getCurrentState().getCurrentTotal();
        gameNumber.setText(String.valueOf(currentTotal));
    }

    private void updateUndoTimes() {
        TextView undoTimes = findViewById(R.id.undoTimes);
        String undoText = "Undo: " + String.valueOf(subtractSquareGame.getUndoBatch()) + " times left";
        undoTimes.setText(undoText);
    }


    public void onclickUndo(View view) {
        if (subtractSquareGame.getUndoBatch() != 0) {
            boolean undoable = subtractSquareGame.undoMove();
            if (undoable) {
                saveToFile(TEMP_FILE_NAME);
                subtractSquareGame.setUndoBatch(subtractSquareGame.getUndoBatch() - 1);
                updateUndoTimes();
                updateCurrentTotal();
                updateProgressContext();
            } else {
                Toast.makeText(this, "It's the first state now", Toast.LENGTH_SHORT).show();
            }
        } else {
            openDialog();
            subtractSquareGame.setUndoBatch(3);
            updateUndoTimes();
        }
    }

    private void openDialog() {
        UndoPaymentDialog undoPaymentDialog = new UndoPaymentDialog();
        undoPaymentDialog.show(getSupportFragmentManager(), "Payment");
    }

    private void updateScore(Context context) {
        if (pcModel) {
            ScoreBoard scoreBoard = new ScoreBoard(context);
            ScoreFactory factory = new ScoreFactory();
            SubtractSquareScore score = (SubtractSquareScore) factory.generateScore(SubtractSquareGame.getGameName());
            subtractSquareGame.setTime(timer.returnStringTime());
            score.takeInStateAndTime(subtractSquareGame.getCurrentState(), subtractSquareGame.getIntTime());
            scoreBoard.addScore(score);
        }
    }
}
