/*
written by: Katrina Schmidt
revision history: 25/9/2020
title: Rock paper scissors

 */

package ca.on.conestogac.schmidt.rockpaperscissors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.widget.Toast;

import java.util.Timer;


import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class SecondFragment extends Fragment {
    ImageView imageView, compImageView;
    ViewGroup rootView;
    public String handPlay, compPlay;
    public static String firstPlay;
    public static boolean firstPlayDone, firstPlayOrientationChange;
    int compPlayNum;
    TextView textView;
    CountDownTimer cTimer = null;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.youImageView);
        compImageView = (ImageView) rootView.findViewById(R.id.computerImageView);
        textView = (TextView) rootView.findViewById(R.id.gameTextView);

        super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState != null) {
            handPlay = savedInstanceState.getString("userPlay", "");
            compPlayNum = savedInstanceState.getInt("compPlay", 0);

            SetImage(handPlay);
            SetComputerPlay(compPlayNum);
            CheckWinner(handPlay);

        }

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
        }

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
        try {
            if(getActivity() != null) {
                ((RockPaperScissors) getActivity().getApplication()).StoreGamePlays(win, loss, tie);
            }
        }catch (NullPointerException ex){
            System.out.println("LOOK HERE NULL : " + ex);
        }


    }
    public void CheckWinner(String userHand) {

        //add scorewinner in here so that it can write to the db

        try {

            textView.setVisibility(View.INVISIBLE);
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
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("userPlay", handPlay);
        outState.putInt("compPlay", compPlayNum);
    }

    public void CheckTime() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Looper.prepare();//Call looper.prepare()
                        Toast.makeText(getActivity(), R.string.keepPlaying, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                },
                3000
        );
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

}














