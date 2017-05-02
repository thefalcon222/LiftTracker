package com.example.ryan.lifttracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.example.ryan.lifttracker.interfaces.ActivityInteraction;
import com.example.ryan.lifttracker.interfaces.RetainedFragmentInteraction;

/**
 * Created by Andrew on 5/2/2017.
 */

public class TaskFragment extends Fragment implements RetainedFragmentInteraction {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ActivityInteraction) context;
    }

    public static final String TAG_TASK_FRAGMENT = "task_fragment";
    private String mActiveFragmentTag;

    private ActivityInteraction activity;

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }
    public TaskFragment() {
        // Required empty public constructor
    }



    @Override
    public void onResume() {
        super.onResume();
    }


    public String getActiveFragmentTag() {
        return mActiveFragmentTag;
    }

    public void setActiveFragmentTag(String s) {
        mActiveFragmentTag = s;
    }

    }

