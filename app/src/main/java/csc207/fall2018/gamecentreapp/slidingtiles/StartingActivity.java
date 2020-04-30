package csc207.fall2018.gamecentreapp.slidingtiles;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import csc207.fall2018.gamecentreapp.DataBase.GameStateDataBase;
import csc207.fall2018.gamecentreapp.GameCentreActivity.UserSpecificActivity;
import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The board manager.
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        boardManager = new BoardManager();
//        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.slidingtile_activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent tmp = new Intent(this, SlidingtileSelectSizeActivity.class);
                destoyLoadGame();
                switchToSelectSize();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFromFile(SAVE_FILENAME);
                boolean loadable = loadFromDataBase();
                if (loadable) {
                    saveToFile(TEMP_SAVE_FILENAME);
                    makeToastLoadedText();
                    switchToGame();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveToFile(SAVE_FILENAME);
                if (hasLoadGame()) {
                    loadFromFile(TEMP_SAVE_FILENAME);
                    saveToDataBase();
                    makeToastSavedText();
                } else {
                    makeToastNotSavedText();
                }
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    private void makeToastNotSavedText() {
        Toast.makeText(this, "No previous game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    private void switchToSelectSize() {
        Intent tmp = new Intent(this, SlidingtileSelectSizeActivity.class);
        startActivity(tmp);
    }

    public void onclickGoBack(View view) {
        Intent tmp = new Intent(this, UserSpecificActivity.class);
        startActivity(tmp);
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
            boardManager = (BoardManager) objectInputStream.readObject();
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
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            File outputFile = new File(getFilesDir(), fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile));
            objectOutputStream.writeObject(boardManager);
            objectOutputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void saveToDataBase() {
        GameStateDataBase dataBase = new GameStateDataBase(this);
        byte[] stream = null;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(boardManager);
            stream = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Session session = Session.getInstance(this);
        dataBase.saveState(session.getCurrentUserName(), BoardManager.getGameName(), stream);
    }

    private boolean loadFromDataBase(/*byte[] byteState*/) {
        GameStateDataBase dataBase = new GameStateDataBase(this);
        Session session = Session.getInstance(this);
        Cursor cursor = dataBase.getStateByGame(session.getCurrentUserName(), BoardManager.getGameName());

        boolean result = cursor.getCount() != 0;

        if (!result) {
            Toast.makeText(this, "No previous played game, start new one!", Toast.LENGTH_SHORT).show();
        } else {
            int stateIndex = cursor.getColumnIndex(GameStateDataBase.COL3);
            cursor.moveToFirst();
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cursor.getBlob(stateIndex));
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                boardManager = (BoardManager) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                // Error in de-serialization
                e.printStackTrace();
            }
        }
        return result;
    }

    private void destoyLoadGame() {
        Session session = Session.getInstance(this);
        GameStateDataBase gameStateDataBase = new GameStateDataBase(this);
        gameStateDataBase.deleteState(session.getCurrentUserName(), BoardManager.getGameName());
    }

    private boolean hasLoadGame() {
        GameStateDataBase dataBase = new GameStateDataBase(this);
        Session session = Session.getInstance(this);
        Cursor cursor = dataBase.getStateByGame(session.getCurrentUserName(), BoardManager.getGameName());
        return cursor.getCount() != 0;
    }

    public void onclickMyScore(View view) {
        Intent intent = new Intent(this, MyScoreActivity.class);
        startActivity(intent);
    }

    public void onclickScoreBoard(View view) {
        Intent intent = new Intent(this, SlidingTileScoreBoardActivity.class);
        startActivity(intent);
    }
}
