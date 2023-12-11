package entities;

enum LessonType {
    THEORY, CODING
}

public class Lesson {
    private int lessonID;
    private String title;
    private int duration;
    private LessonType type;

    public Lesson(int lessonID, String title, int duration, LessonType type) {
        this.lessonID = lessonID;
        this.title = title;
        this.duration = duration;
        this.type = type;
    }

    public int getID() {
        return lessonID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public LessonType getType() {
        return type;
    }
}

