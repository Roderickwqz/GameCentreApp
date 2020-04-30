package csc207.fall2018.gamecentreapp.GameCentreActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import csc207.fall2018.gamecentreapp.R;
import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.User;
//import csc207.fall2018.gamecentreapp.UserManager;

/**
 * A class dealing with Registering.
 */
public class RegisterActivity extends AppCompatActivity {

//    UserDataBase userDataBase;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        userDataBase = new UserDataBase(this);
//    }
//
//    public void onclickRegisterButton(View view) {
//
//        // first input information
//        final EditText userNameInput = (EditText) findViewById(R.id.userName);
//        final EditText userPasswordInput = (EditText) findViewById(R.id.password);
//        String userName = userNameInput.getText().toString();
//        String userPassword = userPasswordInput.getText().toString();
//
//        // create new User
//        User user = new User(userName, userPassword);
//
//        if (!userDataBase.isRegistered(user)) {
//            userDataBase.signUpUser(user);
//            Intent registerIntent = new Intent(getApplicationContext(), UserSpecificActivity.class);
//            startActivity(registerIntent);
//        } else {
//            Toast.makeText(getApplicationContext(), "Already registered", Toast.LENGTH_SHORT).show();
//        }
//    }


    //    private final static String FILE_NAME = "userManager.ser";
//
    /**
     * helper object storing information of current user.
     */
    private Session session;
//    private UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = Session.getInstance(this);
//        userManager = UserManager.getInstance();
//        saveToFile(FILE_NAME);
    }

    /**
     * Dealing with clicking on sign up button.
     *
     * @param view A view of the model
     */
    public void onclickRegisterButton(View view) {

        // first input information
        final EditText userNameInput = (EditText) findViewById(R.id.userName);
        final EditText userPasswordInput = (EditText) findViewById(R.id.password);
        String userName = userNameInput.getText().toString();
        String userPassword = userPasswordInput.getText().toString();

        // create new User
        User user = new User(userName, userPassword);
//        loadFromFile(FILE_NAME);

        // check if the user has not yet registered
        if (userName.equals("") || userPassword.equals("")) {
            Toast.makeText(getApplicationContext(), "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!session.isRegistered(user)) {
            // sign up user and login user
            session.signUp(user);
            Intent registerIntent = new Intent(getApplicationContext(), UserSpecificActivity.class);
            startActivity(registerIntent);
            // already registered
        } else {
            Toast.makeText(getApplicationContext(), "Already has this user", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Dealing with clicking on already have an account.
     *
     * @param view A view of the model
     */

    public void onclickToLogin(View view) {
        Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(returnIntent);
    }

//    /**
//     * Load the board manager from fileName.
//     *
//     * @param fileName the name of the file
//     */
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
//    /**
//     * Save the subtract square to fileName.
//     *
//     * @param fileName the name of the file
//     */
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
