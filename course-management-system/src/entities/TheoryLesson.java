package entities;

public class TheoryLesson extends Lesson {
    public TheoryLesson(int lessonID, String title, int duration) {
        super(lessonID, title, duration, LessonType.THEORY);
    }
}