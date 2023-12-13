package entities;

enum LessonType {
    THEORY, CODING
}

public class Lesson {
    private static int NEXT_ID = 0;
    private int id;
    private int lessonId;
    private String title;
    private int duration;
    private LessonType type;

    public Lesson(int lessonId, String title, int duration, LessonType type) {
        this.id = ++NEXT_ID;
        this.lessonId = lessonId;
        this.title = title;
        this.duration = duration;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getLessonId() {
        return lessonId;
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

