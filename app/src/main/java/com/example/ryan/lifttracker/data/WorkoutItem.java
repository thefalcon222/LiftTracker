package com.example.ryan.lifttracker.data;

/**
 * Created by Ryan on 4/25/2017.
 */

public class WorkoutItem {
    private String date;
    private String name;
    private String description;
    private String reps;

    public WorkoutItem(String date, String name, String description, String reps) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.reps = reps;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getReps() {
        return reps;
    }
}
