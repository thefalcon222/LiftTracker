package com.example.ryan.lifttracker.fragments;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ryan.lifttracker.R;

import static com.example.ryan.lifttracker.R.layout.add_workout;

/**
 * Created by Ryan on 5/2/2017.
 */

public class AddWorkoutFragment extends Fragment implements View.OnClickListener{
    public static final String TAG_ADD_WORKOUT_FRAGMENT = "add_workout_fragment";
    private Button addEx, submit, subtractSet,addSet;
    private LinearLayout fillEX;
    private EditText workoutName, workoutDescription;
    private TextView workoutReps;
    private int reps;

    public static AddWorkoutFragment newInstance() {
        AddWorkoutFragment fragment = new AddWorkoutFragment();
        return fragment;
    }

    public AddWorkoutFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_workout, container, false);
        addEx = (Button) v.findViewById(R.id.addEButton);
        submit = (Button) v.findViewById(R.id.submitWButton);
        //addSet = (Button) v.findViewById(R.id.addSet);
        //subtractSet = (Button) v.findViewById(R.id.subtractSet);
        fillEX = (LinearLayout) v.findViewById(R.id.exercise_container);
        workoutName = (EditText) v.findViewById(R.id.woName);

        addEx.setOnClickListener(this);
        submit.setOnClickListener(this);
        //addSet.setOnClickListener(this);
        //subtractSet.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addEButton:
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService
                        (view.getContext().LAYOUT_INFLATER_SERVICE);
                View exFiller = (View) inflater.inflate(R.layout.exercise_details, null);
                fillEX.addView(exFiller);
                addSet = (Button) exFiller.findViewById(R.id.addSet);
                subtractSet = (Button) exFiller.findViewById(R.id.subtractSet);
                addSet.setOnClickListener(this);
                subtractSet.setOnClickListener(this);
                workoutDescription = (EditText) exFiller.findViewById(R.id.exName);
                workoutReps = (TextView) exFiller.findViewById(R.id.repsView);
                reps = 0;
                break;
            case R.id.submitWButton:
                // Something something send to database
                Calendar c = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    c = Calendar.getInstance();
                    String date = DateFormat.format("MMMM d, yyyy ", c.getTime()).toString();
                }
                String name = workoutName.getText().toString();
                String description = workoutDescription.getText().toString();
                String repString = workoutReps.getText().toString();

                //add to database

                break;
            case R.id.addSet:
                reps++;
                workoutReps.setText(reps + " reps");
                break;
            case R.id.subtractSet:
                reps--;
                workoutReps.setText(reps + " reps");
                break;

            default:
                break;
        }
    }
}
