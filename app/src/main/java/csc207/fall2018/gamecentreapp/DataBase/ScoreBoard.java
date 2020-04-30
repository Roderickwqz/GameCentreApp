package csc207.fall2018.gamecentreapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import csc207.fall2018.gamecentreapp.Score;

public class ScoreBoard extends SQLiteOpenHelper {

    private static final String FILE_NAME = "scoreBoard.db";

    private static final String TABLE_NAME = "scoreList";

    private static final String COL1 = "NAME";

    private static final String COL2 = "GAME";

    private static final String COL3 = "SCORE";

    public ScoreBoard(Context context) {
        super(context, FILE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (NAME VARCHAR, GAME VARCHAR, SCORE VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, score.returnPlayerName());
        contentValues.put(COL2, score.returnGameName());
        contentValues.put(COL3, score.calculateScore());
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getScoreByGame(String game) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE GAME = '" + game + "' ORDER BY SCORE DESC LIMIT 10", null);
    }

    public Cursor getScoreByGameAndName(String name, String game) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE GAME = '" + game + "' AND NAME = '" + name + "' ORDER BY SCORE DESC LIMIT 10", null);
    }

    public void deleteUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();

//        String userName = userManager.getCurrentUserName();
//        UserManager userManager = UserManager.getInstance();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE NAME = '" + userName + "'");
    }

    public void deleteAllScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}


