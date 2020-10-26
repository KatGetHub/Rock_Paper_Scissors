package ca.on.conestogac.schmidt.rockpaperscissors;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class SavedGame extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SecondFragment()).commit();}
    }
}
