/*
written by: Katrina Schmidt
revision history: 25/9/2020
title: Rock paper scissors

 */
package ca.on.conestogac.schmidt.rockpaperscissors;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {
    String firstHandPlay;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.rockBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstHandPlay = "R";
                SecondFragment.newInstance(firstHandPlay);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        view.findViewById(R.id.paperBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstHandPlay = "P";
                SecondFragment.newInstance(firstHandPlay);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        view.findViewById(R.id.scissorsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstHandPlay = "S";
                SecondFragment.newInstance(firstHandPlay);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

            }
        });

    }

}