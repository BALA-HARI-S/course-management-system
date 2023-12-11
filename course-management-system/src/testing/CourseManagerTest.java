package testing;

import entities.Course;
import entities.Lesson;
import entities.Section;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CourseManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseManagerTest {
    private CourseManager courseManager;

    @BeforeEach
    public void setUp() {
        courseManager = new CourseManager();
    }

    @Test
    public void testCreateAndRemoveCourse() {
        Course course = courseManager.createNewCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0, 4.0);
        assertEquals(1, courseManager.getCourseCount());

        courseManager.removeCourseFromList(1);
        assertEquals(0, courseManager.getCourseCount());
    }

    @Test
    public void testAddAndRemoveSection() {
        Course course = courseManager.createNewCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0, 4.0);

        Section section = courseManager.addNewSection(1, 1, "Test Section");
        assertEquals(1, courseManager.getSectionsCount(1));

        assertTrue(courseManager.removeSection(1, 1));
        assertEquals(0, courseManager.getSectionsCount(1));
    }

    @Test
    public void testAddAndRemoveLesson() {
        Course course = courseManager.createNewCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0, 4.0);
        Section section = courseManager.addNewSection(1, 1, "Test Section");

        Lesson theoryLesson = courseManager.addNewLesson(1, 1, 1, "Theory Lesson", 30, "theory");
        Lesson codingLesson = courseManager.addNewLesson(1, 1, 2, "Coding Lesson", 45, "coding");

        assertEquals(2, courseManager.getLessonsCount(1));
        assertTrue(courseManager.removeLesson(1, 1, 1));
        assertEquals(1, courseManager.getLessonsCount(1));
    }

    @Test
    public void testGetLongestLesson() {
        Course course = courseManager.createNewCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0, 4.0);
        Section section = courseManager.addNewSection(1, 1, "Test Section");

        Lesson theoryLesson1 = courseManager.addNewLesson(1, 1, 1, "Theory Lesson 1", 30, "theory");
        Lesson codingLesson1 = courseManager.addNewLesson(1, 1, 2, "Coding Lesson 1", 45, "coding");
        Lesson theoryLesson2 = courseManager.addNewLesson(1, 1, 3, "Theory Lesson 2", 20, "theory");

        List<Lesson> longestLessons = courseManager.getLongestLesson(1);

        assertEquals(1, longestLessons.size());
        assertEquals(45, longestLessons.get(0).getDuration());
    }

}
