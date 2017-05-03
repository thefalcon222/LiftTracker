package com.example.ryan.lifttracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ryan.lifttracker.R;
import com.example.ryan.lifttracker.constants.Constants;
import com.example.ryan.lifttracker.interfaces.HomeScreenInteraction;

/**
 * Created by Ryan on 5/2/2017.
 */

public class HomeScreenFragment extends Fragment implements View.OnClickListener {

    public static final String TAG_HOME_FRAGMENT = "home_fragment";
    private HomeScreenInteraction activity;
    private ImageButton workoutButton, alarmButton;

    public static HomeScreenFragment newInstance() {
        HomeScreenFragment fragment = new HomeScreenFragment();
        return fragment;
    }

    public HomeScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeScreenInteraction) {
            activity = (HomeScreenInteraction) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeScreenInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        workoutButton = (ImageButton) view.findViewById(R.id.workoutButton);
        alarmButton = (ImageButton) view.findViewById(R.id.alarmButton);

        workoutButton.setOnClickListener(this);
        alarmButton.setOnClickListener(this);


        return view;

    }



    @Override
    public void onClick(View view) {

        if(view.equals(workoutButton))
        {
            activity.changeFragment(WorkoutFragment.TAG_WORKOUT_FRAGMENT);
        }
        if(view.equals(alarmButton))
        {
            //activity.changeFragment();
        }

    }

    //Happens when you hit the "Send Email" button
    public void sendEmail()
    {
        String from = Constants.email_from;
        String to = Constants.email_to;
        String subject = Constants.email_subject;

        //We build a string by pulling all database entries into a specific format
        StringBuilder email_body = new StringBuilder();

        
    }
}
