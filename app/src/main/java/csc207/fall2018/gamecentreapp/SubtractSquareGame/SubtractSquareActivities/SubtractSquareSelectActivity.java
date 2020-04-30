package csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;
//import csc207.fall2018.gamecentreapp.UserManager;

public class SubtractSquareSelectActivity extends AppCompatActivity {

    SubtractSquareGame subtractSquareGame;

    //    UserManager userManager;
    private Session session;

    private final static String TEMP_FILE_NAME = "temp_file.ser";

//    private final static String USER_FILE_NAME = "userManager.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_square_select);

        session = Session.getInstance(this);
    }

    public void onclickSelectTwoPlayers(View view) {
        Intent selectTwoPlayers = new Intent(getApplicationContext(), SubtractSquareStartActivity.class);
        startActivity(selectTwoPlayers);
    }

    public void onclickSelectComputer(View view) {
//        loadFromUserFile();
        subtractSquareGame = new SubtractSquareGame(session.getCurrentUserName(), "PC");
        saveToFile(TEMP_FILE_NAME);

        Intent selectComputer = new Intent(getApplicationContext(), SubtractSquareActivity.class);
        selectComputer.putExtra("PC_MODE", true);
        startActivity(selectComputer);
    }

    public void onclickGoBack(View view) {
        Intent goBackIntent = new Intent(getApplicationContext(), SubtractSquareGameCentreActivity.class);
        startActivity(goBackIntent);
    }


//    private void loadFromUserFile() {
//        try {
//            File inputFile = new File(getFilesDir(), USER_FILE_NAME);
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
//
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

}
