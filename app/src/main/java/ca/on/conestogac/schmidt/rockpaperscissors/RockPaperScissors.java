package ca.on.conestogac.schmidt.rockpaperscissors;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RockPaperScissors extends Application {

    private static final String DB_NAME = "db_tax_stats";
    private static final int DB_VERSION = 1;

    private SQLiteOpenHelper helper;

    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbl_stats( " +
                        "record_min INTEGER, win_record INTEGER, loss_record INTEGER, tie_record INTEGER)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        super.onCreate();

    }
    public void StoreGamePlays(int timestamp, int win, int loss, int tie) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_stats " +
                "(record_min, win_record, loss_record, tie_record) " +
                "VALUES( " + timestamp + ", " + win + ", " + loss + ", " + tie + ")");

    }

    public String GetTotalGameScore() {
        SQLiteDatabase db = helper.getReadableDatabase();
        int win, loss, tie;
        String score = "";

        Cursor cursor = db.rawQuery("SELECT SUM(win_record) AS win, SUM(loss_record) AS loss, SUM(tie_record) AS tie FROM tbl_stats",
                null);
        cursor.moveToFirst();
        win = cursor.getInt(0);
        loss = cursor.getInt(1);
        tie = cursor.getInt(2);
        cursor.close();

        score = win + "-" + loss + "-" + tie;
        return score;

    }

    public String GetMinTotalGameScore() {
        SQLiteDatabase db = helper.getReadableDatabase();
        int win, loss, tie;
        String score = "";

        Cursor cursor = db.rawQuery("SELECT SUM(win_record) AS win, SUM(loss_record) AS loss, SUM(tie_record) AS tie FROM tbl_stats" +
                        " WHERE record_min= (SELECT max(record_min) FROM tbl_stats);",
                null);
        cursor.moveToFirst();
        win = cursor.getInt(0);
        loss = cursor.getInt(1);
        tie = cursor.getInt(2);
        cursor.close();

        score = win + "-" + loss + "-" + tie;
        return score;

    }

    public void resetTableStats() {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("DELETE FROM tbl_stats;");
    }
}
