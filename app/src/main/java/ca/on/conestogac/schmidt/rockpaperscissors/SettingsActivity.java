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

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences shardPref;
    public static boolean darkMode;
    public static boolean saveGame;
    public static Activity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activity = this;

        if (darkMode) {
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


        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

        shardPref = PreferenceManager.getDefaultSharedPreferences(this);
        shardPref.edit().putBoolean("darkTheme", darkMode).apply();


    }

    public boolean onPreferenceTreeClick(SwitchPreferenceCompat darkSwitch,
                                         Preference preference) {
        String key = preference.getKey();
        if (key.equals("darkTheme")) {
            // do your work
            return true;
        }
        return false;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SwitchPreferenceCompat darkTheme = (SwitchPreferenceCompat) findPreference("darkTheme");
            final SwitchPreferenceCompat saveGameMode = (SwitchPreferenceCompat) findPreference("saveGame");

            if (darkTheme != null) {
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

                        return true;
                    }
                });
            }
            if(saveGameMode != null){
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
            //  startActivity(getIntent());


        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MainActivity mainActivity = new MainActivity();

        if (item.getItemId() == android.R.id.home) {
            if (mainActivity.active) {
                String darkString = Boolean.toString(darkMode);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("DARK_MODE", darkString);
                startActivity(intent);
            } else {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    public boolean onOptionsItemSelected(MenuItem item){
        //onbackpressed();
        SecondFragment secondFragment = new SecondFragment();
        if(secondFragment.firstPlay == null){
            String darkString = Boolean.toString(darkMode);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("DARK_MODE", darkString);
            startActivity(intent);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.settings,secondFragment).commit();

        }

        return true;
    }
*/
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("rememberDarkTheme", darkMode);
        outState.putBoolean("savedGame", saveGame);

    }
}










