/*
written by: Katrina Schmidt
revision history: 25/9/2020
title: Rock paper scissors

 */
package ca.on.conestogac.schmidt.rockpaperscissors;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    public static boolean active = false;
    androidx.appcompat.widget.Toolbar toolbar;

    private SecondFragment secondFragment;
    private final String SECOND_FRAGMENT_TAG = "secondFragment";
    static final String SECOND_FRAGMENT = "secondFragment";
    static boolean savedGame;
    public static String darkMode;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("saveGame", Activity.MODE_PRIVATE);
        savedGame = sp.getBoolean("saveGame", false);


        darkMode = getIntent().getStringExtra("DARK_MODE");
        //SettingsActivity settingsActivity = new SettingsActivity();
        //darkMode = String.valueOf(settingsActivity.darkMode);
       if(darkMode == null){
            darkMode = "";
        }


        if(darkMode.equals("true")){
            setTheme(R.style.DarkTheme);
        }else{
            setTheme(R.style.AppTheme);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedGame) {

            savedGame = false;
            Intent i = new Intent(MainActivity.this,SavedGame.class);
            startActivity(i);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(myIntent);

        }else if(id == R.id.action_statistics){
            Intent myIntent = new Intent(getBaseContext(), StatisticsActivity.class);
            myIntent.putExtra("DARK_MODE", darkMode);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}