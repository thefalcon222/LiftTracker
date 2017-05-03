package com.example.ryan.lifttracker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryan.lifttracker.R;
import com.example.ryan.lifttracker.data.WorkoutItem;
import com.example.ryan.lifttracker.database.DBConstants;
import com.example.ryan.lifttracker.database.DBController;

import java.util.ArrayList;

/**
 * Created by Ryan on 4/25/2017.
 */

public class WorkoutFragment extends Fragment{

    public static final String TAG_WORKOUT_FRAGMENT = "workout_fragment";

    private ArrayList<WorkoutItem> workouts;

    private ListView memberList;
    private WorkoutItemArrayAdapter workoutListAdapter;

    private DBController database_controller;


    public static WorkoutFragment newInstance() {
        WorkoutFragment fragment = new WorkoutFragment();
        return fragment;
    }

    public WorkoutFragment() {
        // Required empty public constructor
    }

    private class WorkoutItemArrayAdapter extends ArrayAdapter<WorkoutItem> {

        private final Context context;
        private final ArrayList<WorkoutItem> items;
        private int id;


        public WorkoutItemArrayAdapter(Context context,  int id ,ArrayList items) {
            super(context, id, items);
            this.context = context;
            this.items=items;
            this.id = id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.workout_item, parent, false);

            TextView name = (TextView) rowView.findViewById(R.id.workout_name);
            TextView reps = (TextView) rowView.findViewById(R.id.workout_reps);
            TextView date = (TextView) rowView.findViewById(R.id.date);
            TextView description = (TextView) rowView.findViewById(R.id.workout_description);

            name.setText(items.get(position).getName());
            date.setText(items.get(position).getDate());
            reps.setText(items.get(position).getReps());
            description.setText(items.get(position).getDescription());

            return rowView;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workouts = new ArrayList<WorkoutItem>();

        Cursor cursor = null;
        // Lets initiate the database controller
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            database_controller = new DBController(this.getContext(), this.getActivity().getApplication());
        }
        database_controller.OpenDB();
        cursor = database_controller.pullWorkouts();

        do
        {
            //Add the date, followed by appropriate spacing
            String date =cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DATE_NAME));

            //Add the name of the workout, followed by appropriate spacing
            String name =cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_WORKOUT_NAME));

            //Add the description of the workout, followed by appropriate spacing
            String description = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DESCRIPTION_NAME));

            //Add the number of reps, and then a newline for the next entry
            String reps = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_REPS_NAME));
            workouts.add(new WorkoutItem(date,name,description,reps));

        } while (cursor.moveToNext());

        memberList = (ListView)view.findViewById(R.id.workouts);
        workoutListAdapter= new WorkoutItemArrayAdapter(getActivity(), R.layout.workout_item, workouts);
        memberList.setAdapter(workoutListAdapter);



    }


}
