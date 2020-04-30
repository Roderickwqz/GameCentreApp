package csc207.fall2018.gamecentreapp;

import android.content.Context;

import java.util.ArrayList;

import csc207.fall2018.gamecentreapp.DataBase.UserDataBase;

public class Session {

    private static Session session;

    private static UserDataBase userDataBase;

    private static User currentUser;

//    private Context context;


    private Session() {
//        this.context = context;
    }

    public static Session getInstance(Context context) {
        if (session == null) {
            userDataBase = new UserDataBase(context);
            session = new Session();
        }
        return session;
    }

    public boolean isRegistered(User user) {
        return userDataBase.hasUser(user);
    }

    public void signUp(User user) {
        userDataBase.addUser(user);
        currentUser = user;
    }

    public boolean checkUserValidation(User user) {
        return userDataBase.checkUserValidation(user);
    }

    public void loginUser(User user) {
        if (!isLoggedIn() && checkUserValidation(user)) {
            currentUser = user;
        }
    }

    private boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logOut() {
        currentUser = null;
        session = null;
    }

    public String getCurrentUserName() {
        if (currentUser != null) {
            return currentUser.getUserName();
        } else {
            return "No Username";
        }
    }
}

