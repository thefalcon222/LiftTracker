package com.example.ryan.lifttracker.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ryan.lifttracker.R;
import com.example.ryan.lifttracker.constants.Constants;
import com.example.ryan.lifttracker.database.DBConstants;
import com.example.ryan.lifttracker.database.DBController;
import com.example.ryan.lifttracker.email.CreateEmail;
import com.example.ryan.lifttracker.interfaces.HomeScreenInteraction;

/**
 * Created by Ryan on 5/2/2017.
 */

public class HomeScreenFragment extends Fragment implements View.OnClickListener {

    public static final String TAG_HOME_FRAGMENT = "home_fragment";
    private HomeScreenInteraction activity;
    private ImageButton workoutButton, emailButton;

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
        emailButton = (ImageButton) view.findViewById(R.id.emailButton);

        workoutButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);


        return view;

    }



    @Override
    public void onClick(View view) {

        if(view.equals(workoutButton))
        {
            activity.changeFragment(WorkoutFragment.TAG_WORKOUT_FRAGMENT);
        }
        if(view.equals(emailButton))
        {
            //activity.changeFragment(EmailFragment);
            sendEmail();
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
        DBController controller = new DBController(this.getContext(), this.getActivity().getApplication());
        controller.OpenDB();

        //Create the initial email body
        email_body.append("Hello Mark,\n\nThe following email is from the Hoist App.  Below is the list of workouts that have been selected:\n\n");

        //We create a cursor to point to all of the pulled entries
        Cursor cursor = controller.pullWorkouts();
        cursor.moveToFirst();

        //We pull each entry, placing it into a customized String
        //The loop will run until it reaches the last entry
        if(!cursor.isAfterLast()) {
            do {
                //Add the date, followed by appropriate spacing
                email_body.append(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DATE_NAME)));
                email_body.append("  ");

                //Add the name of the workout, followed by appropriate spacing
                email_body.append(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_WORKOUT_NAME)));
                email_body.append("  ");

                //Add the description of the workout, followed by appropriate spacing
                email_body.append(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_DESCRIPTION_NAME)));
                email_body.append("  ");

                //Add the number of reps, and then a newline for the next entry
                email_body.append(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_REPS_NAME)));
                email_body.append("\n");

            } while (cursor.moveToNext());
        }

        //We then finish the email body
        email_body.append("\nFrom the creators of the Hoist App:");
        email_body.append("\nAndrew Walters\nConor Ginnell\nRyan Sullivan\nTanner Hudson");

        //Finally, we call the methods in CreateEmail, sending the email
        CreateEmail emailer = new CreateEmail(to, subject, email_body.toString());

        //TODO ensure that this statement actually works
        Intent send_email = emailer.getChooser();
        startActivity(send_email);
    }
}
