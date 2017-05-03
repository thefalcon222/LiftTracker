package com.example.ryan.lifttracker.fragments;

import android.app.Fragment;
import android.content.Context;
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
import com.example.ryan.lifttracker.database.DBController;

import java.util.ArrayList;

/**
 * Created by Ryan on 4/25/2017.
 */

public class WorkoutFragment extends Fragment{

    public static final String TAG_WORKOUT_FRAGMENT = "workout_fragment";

    private ArrayList<WorkoutItem> workouts;

    private ListView memberList;
    private WorkoutItemArrayAdapter teamMemberListAdapter;

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
        database_controller = new DBController(getApplicationContext(), this, getApplication());
        database_controller.OpenDB();
        cursor = database_controller.pullWorkouts();

        memberList = (ListView)view.findViewById(R.id.workouts);
        teamMemberListAdapter= new WorkoutItemArrayAdapter(getActivity(), R.layout.workout_item, workouts);
        memberList.setAdapter(teamMemberListAdapter);



    }


}
