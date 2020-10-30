package ca.on.conestogac.schmidt.rockpaperscissors;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    private static final String DB_NAME = "db_game_stats";
    private static final int DB_VERSION = 1;
    private SQLiteOpenHelper helper;
    public static String darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("saveGame", Activity.MODE_PRIVATE);
        String dark = sp.getString("theme", "false");
        darkMode = getIntent().getStringExtra("DARK_MODE");

        if(darkMode == null){
            darkMode = "";
        }

        if(dark == null){
            dark = "";
        }
        if(darkMode.equals("true") || dark.equals("true")){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        TextView allTimeRecord = (TextView)findViewById(R.id.allTimeStats);
        TextView minRecord = (TextView)findViewById(R.id.recordStats);

        //allTimeRecord.setText(GetTotalGameScore());
        try {
            allTimeRecord.setText(((RockPaperScissors) getApplication()).GetTotalGameScore());
            minRecord.setText(((RockPaperScissors) getApplication()).GetMinTotalGameScore());

        }catch (NullPointerException ex){
            System.out.println("LOOK HERE NULL : " + ex);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button buttonResetStats = findViewById(R.id.resetStatsBtn);


        buttonResetStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView allTimeStats = findViewById(R.id.allTimeStats);
                TextView minStats = (TextView)findViewById(R.id.recordStats);
                ((RockPaperScissors) getApplication()).resetTableStats();
                allTimeStats.setText("0-0-0");
                minStats.setText("0-0-0");
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}