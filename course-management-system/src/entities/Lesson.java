package entities;

public class Lesson {
    /**
     * Auto-generated lesson id
     */
    private static int NEXT_ID = 0;
    /**
     * Assign auto generated lesson id
     */
    private int id;
    /**
     * Lesson id - get as input
     */
    private int lessonId;
    /**
     * Lesson Title
     */
    private String title;
    /**
     * Lesson duration
     */
    private int duration;
    /**
     * Lesson Type - Either Coding  or Theory Lesson
     */
    private LessonType type;

    public Lesson(int lessonId, String title, int duration, LessonType type) {
        this.id = ++NEXT_ID;
        this.lessonId = lessonId;
        this.title = title;
        this.duration = duration;
        this.type = type;
    }

    /**
     * Get auto-generated Lesson id
     */
    public int getId() {
        return id;
    }

    /**
     * Get Lesson id
     */
    public int getLessonId() {
        return lessonId;
    }

    /**
     * Set Lesson id
     */
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * Get Lesson title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Lesson title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get lesson duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set Lesson duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get Lesson Type
     */
    public LessonType getType() {
        return type;
    }

    /**
     * Set Lesson Type
     */
    public void setType(LessonType type) {
        this.type = type;
    }
}

