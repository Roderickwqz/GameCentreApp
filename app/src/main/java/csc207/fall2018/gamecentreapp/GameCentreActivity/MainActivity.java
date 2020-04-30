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
 * A class dealing with main page.
 */
public class MainActivity extends AppCompatActivity {

//    UserDataBase userDataBase;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        userDataBase = new UserDataBase(this);
//    }
//
//    public void onclickLoginButton(View view) {
//        // first input information
//        final EditText userNameInput = (EditText) findViewById(R.id.userName);
//        final EditText userPasswordInput = (EditText) findViewById(R.id.password);
//        String userName = userNameInput.getText().toString();
//        String userPassword = userPasswordInput.getText().toString();
//
//        User user = new User(userName, userPassword);
//
//        // check if the user has registered
//        if (userDataBase.isRegistered(user)) {
//            // check the validation
//            if (userDataBase.checkUserValidation(user)) {
////                userDataBase.loginUser(user);
//                Intent loginIntent = new Intent(getApplicationContext(), UserSpecificActivity.class);
//                startActivity(loginIntent);
//                // wrong information input
//            } else {
//                Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
//            }
//            // not registered
//        } else {
//            Toast.makeText(getApplicationContext(), "No such user, please register first", Toast.LENGTH_SHORT).show();
//        }
//    }


//——————————————————————————————————————————————————————————————————————————————>>> SQLite


//    private final static String FILE_NAME = "userManager.ser";

//    private UserManager userManager;

    /**
     * helper object storing information of current user.
     */
    private Session session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = Session.getInstance(this);
//        userManager = UserManager.getInstance();
//        saveToFile(FILE_NAME);
    }

    /**
     * Dealing with clicking on Login button.
     *
     * @param view a view of the model
     */
    public void onclickLoginButton(View view) {
        // first input information
        final EditText userNameInput = findViewById(R.id.userName);
        final EditText userPasswordInput = findViewById(R.id.password);
        String userName = userNameInput.getText().toString();
        String userPassword = userPasswordInput.getText().toString();

        User user = new User(userName, userPassword);
//        loadFromFile(FILE_NAME);

        // check if the user has registered
        if (session.isRegistered(user)) {
            // check the validation
            if (session.checkUserValidation(user)) {
                session.loginUser(user);
                Intent loginIntent = new Intent(getApplicationContext(), UserSpecificActivity.class);
                startActivity(loginIntent);
                // wrong information input
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
            // not registered
        } else {
            Toast.makeText(getApplicationContext(), "No such user, please register first", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Dealing with clicking on 'create account'
     * @param view A view of the model.
     */
    public void onclickCreateAccount(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
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
