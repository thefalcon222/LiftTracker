package com.example.ryan.lifttracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ryan.lifttracker.fragments.AddWorkoutFragment;
import com.example.ryan.lifttracker.fragments.HomeScreenFragment;
import com.example.ryan.lifttracker.fragments.TaskFragment;
import com.example.ryan.lifttracker.fragments.WorkoutFragment;
import com.example.ryan.lifttracker.interfaces.ActivityInteraction;
import com.example.ryan.lifttracker.interfaces.HomeScreenInteraction;
import com.example.ryan.lifttracker.interfaces.RetainedFragmentInteraction;

public class MainActivity extends AppCompatActivity implements HomeScreenInteraction, ActivityInteraction {

    private Fragment homeScreenFragment, workoutFragment, taskFragment;

    private FragmentManager fragmentManager;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fragmentManager = getSupportFragmentManager();

        taskFragment = (TaskFragment) fragmentManager.findFragmentByTag(TaskFragment.TAG_TASK_FRAGMENT);

        if (taskFragment == null) {

            taskFragment = new TaskFragment();
            fragmentManager.beginTransaction().add(taskFragment, TaskFragment.TAG_TASK_FRAGMENT).commit();
        }

        if (savedInstanceState == null) {
            homeScreenFragment = new HomeScreenFragment();
            // Set dashboard fragment to be the default fragment shown
            ((RetainedFragmentInteraction) taskFragment).setActiveFragmentTag(HomeScreenFragment.TAG_HOME_FRAGMENT);
            fragmentManager.beginTransaction().replace(R.id.Frame, homeScreenFragment).commit();
        } else {
            //workoutFragment = fragmentManager.findFragmentByTag(WorkoutFragment.TAG_WORKOUT_FRAGMENT);
            ((RetainedFragmentInteraction) taskFragment).setActiveFragmentTag(WorkoutFragment.TAG_WORKOUT_FRAGMENT);
            workoutFragment = fragmentManager.findFragmentByTag(WorkoutFragment.TAG_WORKOUT_FRAGMENT);
        }

    }

    @Override
    public void changeFragment(String fragment_name) {


        Fragment fragment;
        Class fragmentClass = null;
        if (fragment_name.equals(WorkoutFragment.TAG_WORKOUT_FRAGMENT)) {
            fragmentClass = WorkoutFragment.class;

            Log.d("HW2", "workout fragment selected");
        } else if (fragment_name.equals(AddWorkoutFragment.TAG_ADD_WORKOUT_FRAGMENT)) {
            fragmentClass = AddWorkoutFragment.class;

            Log.d("HW2", "add workout fragment selected");
        }

        try {
            if (fragmentClass != null) {
                fragment = (Fragment) fragmentClass.newInstance();


                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.replace(R.id.Frame, fragment,
                        ((RetainedFragmentInteraction) taskFragment).getActiveFragmentTag());
                ft.addToBackStack(null);
                ft.commit();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void InitiateLoginActivity() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_home) {
            changeFragment(HomeScreenFragment.TAG_HOME_FRAGMENT);
        } else if (id == R.id.action_set_alert) {
            //Set an alert

        } else if (id == R.id.action_logout) {
            // Log the user out
            prefs.edit().remove("status").apply();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.action_add_workout) {
            //Add a workout
            changeFragment(AddWorkoutFragment.TAG_ADD_WORKOUT_FRAGMENT);
        }

        return super.onOptionsItemSelected(item);
    }
}