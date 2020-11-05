package ca.on.conestogac.schmidt.rockpaperscissors;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class SavedGame extends AppCompatActivity {
    public static String darkMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MainActivity mainActivity = new MainActivity();
        mainActivity.active = false;

        SharedPreferences sp = getSharedPreferences("saveGame", Activity.MODE_PRIVATE);
        String dark = sp.getString("theme", "false");
        darkMode = getIntent().getStringExtra("DARK_MODE");
        //SettingsActivity settingsActivity = new SettingsActivity();
        //darkMode = String.valueOf(settingsActivity.darkMode);
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
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SecondFragment()).commit();}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
