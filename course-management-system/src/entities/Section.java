package entities;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private int sectionID;
    private String title;
    private List<Lesson> lessons;

    public Section(int sectionID, String title) {
        this.sectionID = sectionID;
        this.title = title;
        this.lessons = new ArrayList<Lesson>();
    }

    public int getID() {
        return sectionID;
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

}

