package entities;

import java.util.ArrayList;
import java.util.List;

public class Section {
    /**
     * Auto-generated section id
     */
    private static int NEXT_ID = 0;
    /**
     * Assign auto generated section id
     */
    private int id;
    /**
     * Section id - get as input
     */
    private int sectionId;
    /**
     * Section Title
     */
    private String title;
    /**
     * Section List
     */
    private List<Lesson> lessons;

    public Section(int sectionId, String title) {
        this.id = ++NEXT_ID;
        this.sectionId = sectionId;
        this.title = title;
        this.lessons = new ArrayList<Lesson>();
    }

    /**
     * Get Auto - generated Section id
     */
    public int getId() {
        return id;
    }

    /**
     * Get Section id
     */
    public int getSectionId() {
        return sectionId;
    }

    /**
     * Set Section id
     */
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Get Section title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Section title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Lessons List
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Add Lesson to List
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

}

