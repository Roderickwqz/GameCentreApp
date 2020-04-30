package csc207.fall2018.gamecentreapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import csc207.fall2018.gamecentreapp.Session;
import csc207.fall2018.gamecentreapp.SubtractSquareGame.SubtractSquareGame;
//import csc207.fall2018.gamecentreapp.UserManager;

public class GameStateDataBase extends SQLiteOpenHelper {

    private static final String FILE_NAME = "gameDataBase.db";

    private static final String TABLE_NAME = "gameStates";

    public static final String COL1 = "NAME";

    public static final String COL2 = "GAME";

    public static final String COL3 = "GAME_STATE";

//    public static final String COL4 = "TIME";



    public GameStateDataBase(Context context) {
        super(context, FILE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (NAME VARCHAR, GAME VARCHAR, GAME_STATE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveState(String userName, String gameName, byte[] state) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteState(userName, SubtractSquareGame.getGameName());

//        UserManager userManager = UserManager.getInstance();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, userName/*userManager.getCurrentUserName()*/);
        contentValues.put(COL2, gameName);
        contentValues.put(COL3, state);
//        contentValues.put(COL4, time);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void deleteState(String userName, String gameName) {
        SQLiteDatabase db = this.getWritableDatabase();

//        String userName = userManager.getCurrentUserName();
//        UserManager userManager = UserManager.getInstance();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE NAME = '" + userName + "' AND GAME = '" + gameName + "'");
    }

    public Cursor getStateByGame(String userName, String gameName) {
        SQLiteDatabase db = this.getWritableDatabase();

//        UserManager userManager = UserManager.getInstance();
//        String userName = userManager.getCurrentUserName();

        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE NAME = '" + userName + "' AND GAME = '" + gameName + "'", null);
    }

    public void deleteUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();

//        String userName = userManager.getCurrentUserName();
//        UserManager userManager = UserManager.getInstance();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE NAME = '" + userName + "'");
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
