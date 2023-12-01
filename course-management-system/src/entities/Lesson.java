package entities;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private static int LAST_ASSIGNED_ID = 0;
    private int id;
    private String title;
    private int duration;


    public Lesson(String title, int duration) {
        this.id = ++LAST_ASSIGNED_ID;
        this.title = title;
        this.duration = duration;
    }

    public int getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() { return duration; }
    @Override
    public String toString(){
        return "Lesson " + id + " - " + title;

    }

}

