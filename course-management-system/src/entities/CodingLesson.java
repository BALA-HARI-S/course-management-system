package entities;

public class CodingLesson extends Lesson {
    public CodingLesson(int lessonID, String title, int duration) {
        super(lessonID, title, duration, LessonType.CODING);
    }
}