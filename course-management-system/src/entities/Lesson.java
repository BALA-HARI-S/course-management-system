package entities;

public class Lesson {
    private int lessonID;
    private String title;
    private int duration;


    public Lesson(int lessonID, String title, int duration) {
        this.lessonID = lessonID;
        this.title = title;
        this.duration = duration;
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

    public int getDuration() { return duration; }

}

