package com.example.ryan.lifttracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        View view = inflater.inflate(R.layout.add_workout, container, false);

        LinearLayout fillUp = (LinearLayout) view.findViewById(R.id.exercise_container);
        LinearLayout fillSets = (LinearLayout) view.findViewById(R.id.row_container);

        View rowFiller = (View) inflater.inflate(R.layout.exercise_rows, null);
        fillSets.addView(rowFiller);
        View exFiller = (View) inflater.inflate(R.layout.exercise_details, null);
        fillUp.addView(exFiller);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }
}
