package entities;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private static int NEXT_ID = 0;
    private int id;
    private int sectionId;
    private String title;
    private List<Lesson> lessons;

    public Section(int sectionId, String title) {
        this.id = NEXT_ID++;
        this.sectionId = sectionId;
        this.title = title;
        this.lessons = new ArrayList<Lesson>();
    }

    public int getId() {
        return id;
    }

    public int getSectionId() {
        return sectionId;
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

