package csc207.fall2018.gamecentreapp.GameCentreActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import csc207.fall2018.gamecentreapp.DataBase.GameStateDataBase;
import csc207.fall2018.gamecentreapp.DataBase.ScoreBoard;
import csc207.fall2018.gamecentreapp.Dialogs.DeleteUserDialog;
import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareActivities.SubtractSquareGameCentreActivity;
import csc207.fall2018.gamecentreapp.Sudoku.SudokuGameStartingActivity;
//import csc207.fall2018.gamecentreapp.UserManager;
import csc207.fall2018.gamecentreapp.slidingtiles.StartingActivity;

/**
 * A class dealing with user centre page
 */
public class UserSpecificActivity extends AppCompatActivity {

    //    private UserManager userManager;
//
//    private final static String FILE_NAME = "userManager.ser";
    /**
     * helper object storing information of current user
     */
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specific);

//        loadFromFile(FILE_NAME);

        // show username on top
        session = Session.getInstance(this);
        TextView userName = findViewById(R.id.centreUserName);
        String userNameInstance = session.getCurrentUserName();
        userName.setText(userNameInstance);
    }


    /**
     * Dealing with clicking return
     *
     * @param view a view of the model.
     */
    public void onClickLogOut(View view) {
        session.logOut();
//        saveToFile(FILE_NAME);
        Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(returnIntent);
    }

    /**
     * Dealing with clicking on sliding tile.
     * @param view a view of the model.
     */
    public void onClickSlidingTileGame(View view) {
        Intent slidingTileGameIntent = new Intent(getApplicationContext(), StartingActivity.class);
        startActivity(slidingTileGameIntent);
    }

    /**
     * Dealing with clicking on subtract square
     * @param view a view of the model.
     */
    public void onclickSubtractSquareGame(View view) {
        Intent startSubtractSquare = new Intent(getApplicationContext(), SubtractSquareGameCentreActivity.class);
        startActivity(startSubtractSquare);
    }

    /**
     * Dealing with clicking on sudoku
     * @param view a view of the model
     */
    public void onclickSudokugame(View view) {
        Intent startSudoku = new Intent(getApplicationContext(), SudokuGameStartingActivity.class);
        startActivity(startSudoku);
    }

    /**
     * Dealing with clicking on Delete User
     * @param view a view of the model
     */
    public void onclickDeleteUser(View view) {
        openDialog();
    }

    /**
     * Call DeleteUserDialog class to delete current user in the database.
     */
    private void openDialog() {
        DeleteUserDialog deleteUserDialog = new DeleteUserDialog();
        deleteUserDialog.show(getSupportFragmentManager(), "Delete User Info");
    }

    /**
     * Dealing with clicking on delete all game data
     * @param view a view of the model
     */
    public void onclickDeleteAllInfo(View view) {
        ScoreBoard scoreBoard = new ScoreBoard(this);
        GameStateDataBase gameStateDataBase = new GameStateDataBase(this);
        scoreBoard.deleteAllScores();
        gameStateDataBase.deleteAll();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

//    private void loadFromFile(String fileName) {
//        try {
//            File inputFile = new File(getFilesDir(), fileName);
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(inputFile));
//            userManager = (UserManager) objectInputStream.readObject();
//            objectInputStream.close();
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        }
//    }
//
//    public void saveToFile(String fileName) {
//        try {
//            File outputFile = new File(getFilesDir(), fileName);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile));
//            objectOutputStream.writeObject(userManager);
//            objectOutputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }
}

