package ca.on.conestogac.schmidt.rockpaperscissors;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    private static final String DB_NAME = "db_game_stats";
    private static final int DB_VERSION = 1;
    private SQLiteOpenHelper helper;
    public static String darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        darkMode = getIntent().getStringExtra("DARK_MODE");

        if(darkMode == null){
            darkMode = "";
        }
        if(darkMode.equals("true")){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        TextView allTimeRecord = (TextView)findViewById(R.id.allTimeStats);
        //allTimeRecord.setText(GetTotalGameScore());
        try {
            allTimeRecord.setText(((RockPaperScissors) getApplication()).GetTotalGameScore());
        }catch (NullPointerException ex){
            System.out.println("LOOK HERE NULL : " + ex);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    /*
        public void CreateDatabase(){
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
        }


        public void StoreGamePlays(int win, int loss, int tie) {
            try {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("INSERT INTO tbl_stats " +
                        "(win_record, loss_record, tie_record) " +
                        "VALUES(" + win + ", " + loss + ", " + tie + ")");
            }catch (Exception ex){
                System.out.println("HERE : " + ex);
            }
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
    */
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}