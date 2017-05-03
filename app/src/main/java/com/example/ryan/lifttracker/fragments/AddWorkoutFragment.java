package com.example.ryan.lifttracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ryan.lifttracker.R;

import static com.example.ryan.lifttracker.R.layout.add_workout;

/**
 * Created by Ryan on 5/2/2017.
 */

public class AddWorkoutFragment extends Fragment implements View.OnClickListener{
    public static final String TAG_ADD_WORKOUT_FRAGMENT = "add_workout_fragment";

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
        Button addEx = (Button) v.findViewById(R.id.addEButton);
        Button submit = (Button) v.findViewById(R.id.submitWButton);
        Button addSet = (Button) v.findViewById(R.id.addSet);
        Button subtractSet = (Button) v.findViewById(R.id.subtractSet);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addEButton:

                LinearLayout fillEX = (LinearLayout) view.findViewById(R.id.exercise_container);
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService
                        (view.getContext().LAYOUT_INFLATER_SERVICE);
                View exFiller = (View) inflater.inflate(R.layout.exercise_details, null);
                fillEX.addView(exFiller);

                break;
            case R.id.submitWButton:
                // Something something send to database
                break;
            case R.id.addSet:

                LinearLayout fillROWS = (LinearLayout) view.findViewById(R.id.row_container);
                LayoutInflater rowInflater = (LayoutInflater)view.getContext().getSystemService
                        (view.getContext().LAYOUT_INFLATER_SERVICE);
                View rowFiller = rowInflater.inflate(R.layout.exercise_rows, null);
                fillROWS.addView(rowFiller);

                break;
            case R.id.subtractSet:
                // Something something delete a row
                break;

            default:
                break;
        }
    }
}
