package entities;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private static int LAST_ASSIGNED_ID = 0;
    private int staticID;
    private int sectionID;
    private String title;
    private List<Lesson> lessons;

    public Section(int sectionNo, String title) {
        this.staticID = ++LAST_ASSIGNED_ID;
        this.sectionID = sectionNo;
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

