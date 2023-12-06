package entities;

public class Lesson {
    private static int LAST_ASSIGNED_ID = 0;
    private int staticID;
    private int lessonNO;
    private String title;
    private int duration;


    public Lesson(int lessonNO, String title, int duration) {
        this.staticID = ++LAST_ASSIGNED_ID;
        this.lessonNO = lessonNO;
        this.title = title;
        this.duration = duration;
    }

    public int getID() {
        return staticID;
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
        return "Lesson " + staticID + " - " + title;

    }

}

