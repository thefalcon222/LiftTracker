package com.example.ryan.lifttracker.fragments;

import android.content.ContentValues;
import android.icu.util.Calendar;
import android.os.Build;
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
import android.widget.Toast;

import com.example.ryan.lifttracker.R;
import com.example.ryan.lifttracker.database.DBConstants;
import com.example.ryan.lifttracker.database.DBController;

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
    private DBController database_controller;

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
                String date = "1/1/1";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    c = Calendar.getInstance();
                    date = DateFormat.format("MMMM d, yyyy ", c.getTime()).toString();
                }
                String name = workoutName.getText().toString();
                String description = workoutDescription.getText().toString();
                String repString = workoutReps.getText().toString();

                //add to database
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    database_controller = new DBController(this.getContext(), this.getActivity().getApplication());
                }
                database_controller.OpenDB();

                ContentValues values = new ContentValues();
                values.put(DBConstants.COLUMN_DATE_NAME,date);
                values.put(DBConstants.COLUMN_DESCRIPTION_NAME,description);
                values.put(DBConstants.COLUMN_REPS_NAME,repString);
                values.put(DBConstants.COLUMN_WORKOUT_NAME,name);
                database_controller.insert(values);

                database_controller.CloseDB();
                database_controller = null;

                workoutDescription.setText("");
                workoutReps.setText("");
                workoutName.setText("");
                Log.d("Hoist", "workout saved");
                Toast.makeText(this.getContext(), "Workout Logged", Toast.LENGTH_LONG).show();
                break;
            case R.id.addSet:
                reps++;
                workoutReps.setText(reps + " reps");
                break;
            case R.id.subtractSet:
                reps--;
                if(reps<0)
                    reps=0;
                workoutReps.setText(reps + " reps");
                break;

            default:
                break;
        }
    }
}
