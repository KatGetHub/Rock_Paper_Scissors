package ca.on.conestogac.schmidt.rockpaperscissors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

//going back from save game to main page when theme change is a little weird, maybe get rid of the second if statment,
//also maybe try and get a way for the page to go back to the main game with the notification

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences shardPref;
    public static boolean darkMode;
    public static boolean saveGame;
    public static Activity activity = null;
    public static final String SAVE_GAME = "saveGame";
    public static boolean themeChanged;
    private static String dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sp = getSharedPreferences("saveGame", Activity.MODE_PRIVATE);
        saveGame = sp.getBoolean("saveGame", false);
        dark = sp.getString("theme", "false");
        darkMode =  Boolean.parseBoolean(sp.getString("theme", "false"));

        activity = this;

        if(dark == null){
            dark = "";
        }
        if (darkMode || dark.equals("true")) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        if (savedInstanceState != null) {
            darkMode = savedInstanceState.getBoolean("rememberDarkTheme", false);

        }

        //Boolean switchState = simpleSwitch.isChecked();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        themeChanged = false;

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

        shardPref = PreferenceManager.getDefaultSharedPreferences(this);
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SwitchPreferenceCompat darkTheme = (SwitchPreferenceCompat) findPreference("darkTheme");
            SwitchPreferenceCompat saveGameMode = (SwitchPreferenceCompat) findPreference("saveGame");


            if (darkTheme != null) {
                if(dark.equals("true") || darkMode){
                    darkTheme.setChecked(true);
                }else{
                    darkTheme.setChecked(false);
                }
                darkTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isDarkThemeObject) {
                        boolean isDarkTheme = (Boolean) isDarkThemeObject;
                        if (isDarkTheme) {
                            darkMode = !darkMode;
                           // SettingsActivity.activity.recreate();

                        } else {
                            darkMode = false;
                        }
                        themeChanged = true;
                        return true;
                    }
                });
            }
            if(saveGameMode != null){
                if(saveGame){
                    saveGameMode.setChecked(true);
                }else{
                    saveGameMode.setChecked(false);
                }
                saveGameMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference arg0, Object isSaveGameObject) {
                        boolean isSaveGame = (Boolean) isSaveGameObject;
                        if (isSaveGame) {
                            saveGame = !saveGame;

                        } else {
                            saveGame = false;
                        }
                        return true;
                    }
                });

            }
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

        }
    }

    public void SaveGame(){
        String darkString = Boolean.toString(darkMode);

        SharedPreferences sp = getSharedPreferences(SAVE_GAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("saveGame", saveGame);
        editor.putString("theme", darkString);

        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MainActivity mainActivity = new MainActivity();

        SaveGame();

        if (item.getItemId() == android.R.id.home) {
            if (mainActivity.active || themeChanged ) {
                //goes back to main activity if dark theme or still in main activity
                String darkString = Boolean.toString(darkMode);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("DARK_MODE", darkString);
                startActivity(intent);
            }
            else if(saveGame){
                //goes back to saved game if nothing else has been pressed
                String darkString = Boolean.toString(darkMode);
                Intent intent = new Intent(getApplicationContext(), SavedGame.class);
                intent.putExtra("DARK_MODE", darkString);
                startActivity(intent);
           }else{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("rememberDarkTheme", darkMode);
        outState.putBoolean("savedGame", saveGame);

    }
}










