package entities;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private static int LAST_ASSIGNED_ID = 0;
    private int id;
    private String title;
    private List<Lesson> lessons;

    public Section(String title) {
        this.id = ++LAST_ASSIGNED_ID;
        this.title = title;
        this.lessons = new ArrayList<Lesson>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    @Override
    public String toString() {
        return "Section " + id + " - " + title;
    }
}

