/*
written by: Katrina Schmidt
revision history: 25/9/2020
title: Rock paper scissors

 */

package ca.on.conestogac.schmidt.rockpaperscissors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Timer;


import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class SecondFragment extends Fragment {
    ImageView imageView, compImageView;
    ViewGroup rootView;
    androidx.appcompat.widget.Toolbar toolbar;
    public String handPlay, compPlay;
    public boolean saveGame;
    public static String firstPlay;
    public static boolean firstPlayDone, firstPlayOrientationChange;
    int compPlayNum;
    TextView textView;
    CountDownTimer cTimer = null;
    private final String SOME_VALUE_KEY = "someValueToSave";
    public static String USER_PLAY;
    public static String COMP_PLAY;
    public static String GAME_OUTCOME;
    public static final String MY_PREFS_NAME = "mySavedGame";
    public boolean gameSaved;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        if(getContext() != null) {
            SharedPreferences sp = getContext().getSharedPreferences("saveGame", Activity.MODE_PRIVATE);
            gameSaved = sp.getBoolean("saveGame", false);
        }
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.youImageView);
        compImageView = (ImageView) rootView.findViewById(R.id.computerImageView);
        textView = (TextView) rootView.findViewById(R.id.gameTextView);

        super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState != null) {
            handPlay = savedInstanceState.getString("userPlay", "");
            compPlayNum = savedInstanceState.getInt("compPlay", 0);
            saveGame = savedInstanceState.getBoolean("saveGame", false);

            SetImage(handPlay);
            SetComputerPlay(compPlayNum);
            CheckWinner(handPlay);

        }
        if(gameSaved){
            if(getContext() != null) {
                SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                String userPlayer = prefs.getString("savedHand", "P");
                String compPlayer = prefs.getString("savedComp", "P");
                String outcome = prefs.getString("savedOutcome", "Who won");
                if(userPlayer != null) {
                    SetPlayerImage(userPlayer);
                }
                if(compPlayer != null) {
                    SetCompImage(compPlayer);
                }
                textView.setText(outcome);
            }
        }else {
            //checks for the first play from the first fragment
            if (!firstPlayDone) {
                SetImage(firstPlay);
                if (firstPlayOrientationChange) {
                    SetComputerPlay(compPlayNum);
                } else {
                    ComputerPlayer();
                }

                CheckWinner(firstPlay);
                firstPlayOrientationChange = true;
                cancelTimer();
                startTimer();
            }else{
                SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                String userPlayer = prefs.getString("savedHand", "P");
                String compPlayer = prefs.getString("savedComp", "P");
                String outcome = prefs.getString("savedOutcome", "Who won");
                if(userPlayer != null) {
                    SetPlayerImage(userPlayer);
                }
                if(compPlayer != null) {
                    SetCompImage(compPlayer);
                }
                textView.setText(outcome);
            }

        }

        MainActivity mainActivity = new MainActivity();
        mainActivity.active = false;

        return rootView;
    }


    //this calculates the computers moves and sets them
    public void ComputerPlayer() {
        Random randPlay = new Random();
        int min = 1;
        int max = 3;

        int randomNum = randPlay.nextInt((max - min) + 1) + min;
        compPlayNum = randomNum;
        SetComputerPlay(randomNum);

    }


    public void SetImageAnimation() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                    }
                },
                3000
        );
    }



    public void SetComputerPlay(int playNumber) {

        compImageView.setVisibility(View.INVISIBLE);
        switch (playNumber) {
            case 1:
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            compImageView.setVisibility(View.VISIBLE);
                                            compImageView.setImageResource(R.drawable.ic_rock);
                                            compImageView.setAlpha(0f);
                                            compImageView.animate().alpha(1f).setDuration(1000);
                                        }
                                    });
                                }
                            }
                        }, 1000);

                compPlay = "R";
                break;
            case 2:
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            compImageView.setVisibility(View.VISIBLE);
                                            compImageView.setImageResource(R.drawable.ic_paper);
                                            compImageView.setAlpha(0f);
                                            compImageView.animate().alpha(1f).setDuration(1000);
                                        }
                                    });
                                }
                            }
                        }, 1000);

                compPlay = "P";
                break;
            case 3:
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            compImageView.setVisibility(View.VISIBLE);
                                            compImageView.setImageResource(R.drawable.ic_scissors);
                                            compImageView.setAlpha(0f);
                                            compImageView.animate().alpha(1f).setDuration(1000);
                                        }
                                    });
                                }
                            }
                        }, 1000);

                compPlay = "S";
                break;
        }

    }

    public void SetCompImage(String comp) {
        switch (comp) {
            case "R":
                compImageView.setImageResource(R.drawable.ic_rock);
                break;

            case "P":
                compImageView.setImageResource(R.drawable.ic_paper);
                break;

            case "S":
                compImageView.setImageResource(R.drawable.ic_scissors);
                break;

        }

    }
    public void SetPlayerImage(String hand) {
        switch (hand) {
            case "R":
                imageView.setImageResource(R.drawable.ic_rock);
                break;

            case "P":
                imageView.setImageResource(R.drawable.ic_paper);
                break;

            case "S":
                imageView.setImageResource(R.drawable.ic_scissors);
                break;

        }

    }

    //this sets the users drawable image to their play
    public void SetImage(String hand) {
        switch (hand) {
            case "R":
                imageView.setImageResource(R.drawable.ic_rock);
                break;

            case "P":
                imageView.setImageResource(R.drawable.ic_paper);
                break;

            case "S":
                imageView.setImageResource(R.drawable.ic_scissors);
                break;

        }
            imageView.setAlpha(0f);
            imageView.animate().alpha(1f).setDuration(1000);

    }

    public void SaveGame(String user, String comp, String outcome){
        if(getContext() != null) {
            Context context = getActivity();

            SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString("savedHand", user);
            editor.putString("savedComp", comp);
            editor.putString("savedOutcome", outcome);
            editor.apply();
        }
    }



    //the listeners and setting the play variables for the user
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.rockBtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetImage("R");
                firstPlayDone = true;
                handPlay = "R";
                ComputerPlayer();
                CheckWinner(handPlay);
                cancelTimer();
                startTimer();
            }
        });

        view.findViewById(R.id.paperBtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetImage("P");
                firstPlayDone = true;
                handPlay = "P";
                ComputerPlayer();
                CheckWinner(handPlay);
                cancelTimer();
                startTimer();
            }
        });

        view.findViewById(R.id.scissorsBtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetImage("S");
                firstPlayDone = true;
                handPlay = "S";
                ComputerPlayer();
                CheckWinner(handPlay);
                cancelTimer();
                startTimer();

            }
        });


    }

    public static FirstFragment newInstance(String value) {
        FirstFragment firstFragment = new FirstFragment();
        firstPlayDone = false;
        firstPlayOrientationChange = false;
        Bundle args = new Bundle();
        args.putString("message", value);
        firstFragment.setArguments(args);
        firstPlay = args.getString("message");

        return firstFragment;
    }

    //just trying to simplify and not do a bunch of if statements
    public static String DeterminePlays(String humanPlayer, String computerPlayer) {
        String win = "";

        if (humanPlayer.equals("R") && computerPlayer.equals("S")) {
            win = "W";
        }
        if (humanPlayer.equals("S") && computerPlayer.equals("P")) {
            win = "W";
        }
        if (humanPlayer.equals("P") && computerPlayer.equals("R")) {
            win = "W";
        }

        return win;
    }

    public void SendGameStats(int win, int loss, int tie){
        int timeStamp =  Math.round(System.currentTimeMillis() / 1000);

        try {
            if(getActivity() != null) {
                ((RockPaperScissors) getActivity().getApplication()).StoreGamePlays(timeStamp, win, loss, tie);
            }
        }catch (NullPointerException ex){
            System.out.println("LOOK HERE NULL : " + ex);
        }


    }
    public void CheckWinner(String userHand) {
        String outcome = "";
        //add scorewinner in here so that it can write to the db
        try {

           // textView.setVisibility(View.INVISIBLE);
            if (userHand.equals(compPlay)) {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView.setVisibility(View.VISIBLE);
                                            textView.setText(R.string.tied);
                                            SendGameStats(0, 0, 1);
                                        }
                                    });
                                }
                            }
                        }, 2000);
                outcome =  getResources().getString(R.string.tied);
            } else if (DeterminePlays(userHand, compPlay).equals("W")) {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView.setVisibility(View.VISIBLE);
                                            textView.setText(R.string.youWon);
                                            SendGameStats(1, 0, 0);
                                        }
                                    });
                                }
                            }
                        }, 2000);
                outcome =  getResources().getString(R.string.youWon);
            } else {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView.setVisibility(View.VISIBLE);
                                            textView.setText(R.string.computerWon);
                                            SendGameStats(0, 1, 0);
                                        }
                                    });
                                }
                            }
                        }, 2000);
                outcome =  getResources().getString(R.string.computerWon);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        SaveGame(userHand, compPlay, outcome);
    }

    public void onSaveInstanceState(Bundle outState) {
        SettingsActivity settingsActivity = new SettingsActivity();
        saveGame = settingsActivity.saveGame;
        super.onSaveInstanceState(outState);
        outState.putString("userPlay", handPlay);
        outState.putInt("compPlay", compPlayNum);
        outState.putBoolean("saveGame", saveGame);

    }


    //start timer function
    void startTimer() {
        try {
            cTimer = new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    if(getActivity() != null) {
                        Toast.makeText(getActivity(), R.string.keepPlaying, Toast.LENGTH_LONG).show();
                    }
                    Looper.loop();
                }
            };
            cTimer.start();
        }catch (Exception ex){
           // cancelTimer();
            System.out.println("you left the page");
        }
    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    @Override
    public void onStop() {
        if(getActivity() != null) {
            getActivity().startService(new Intent(getActivity(), NotificationService.class));
        }
        super.onStop();
    }


}














