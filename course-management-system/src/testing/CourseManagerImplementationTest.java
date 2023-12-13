package testing;

import entities.Course;
import entities.Lesson;
import entities.Section;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CourseManagerImplementation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseManagerImplementationTest {
    private CourseManagerImplementation courseManagerImplementation;

    @BeforeEach
    public void setUp() {
        courseManagerImplementation = new CourseManagerImplementation();
    }

    @Test
    public void testCreateAndRemoveCourse() {
        Course course = courseManagerImplementation.createCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0);
        assertEquals(1, courseManagerImplementation.getCourseCount());

        courseManagerImplementation.removeCourseFromList(1);
        assertEquals(0, courseManagerImplementation.getCourseCount());
    }

    @Test
    public void testAddAndRemoveSection() {
        Course course = courseManagerImplementation.createCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0);

        Section section = courseManagerImplementation.addNewSection(1, 1, "Test Section");
        assertEquals(1, courseManagerImplementation.getSectionsCount(1));

        assertTrue(courseManagerImplementation.removeSection(1, 1));
        assertEquals(0, courseManagerImplementation.getSectionsCount(1));
    }

    @Test
    public void testAddAndRemoveLesson() {
        Course course = courseManagerImplementation.createCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0);
        Section section = courseManagerImplementation.addNewSection(1, 1, "Test Section");

        Lesson theoryLesson = courseManagerImplementation.addNewLesson(1, 1, 1, "Theory Lesson", 30, "theory");
        Lesson codingLesson = courseManagerImplementation.addNewLesson(1, 1, 2, "Coding Lesson", 45, "coding");

        assertEquals(2, courseManagerImplementation.getLessonsCount(1));
        assertTrue(courseManagerImplementation.removeLesson(1, 1, 1));
        assertEquals(1, courseManagerImplementation.getLessonsCount(1));
    }

    @Test
    public void testGetLongestLesson() {
        Course course = courseManagerImplementation.createCourse(1, "Test Course", "Test Author", "01/01/2023", 100.0);
        Section section = courseManagerImplementation.addNewSection(1, 1, "Test Section");

        Lesson theoryLesson1 = courseManagerImplementation.addNewLesson(1, 1, 1, "Theory Lesson 1", 30, "theory");
        Lesson codingLesson1 = courseManagerImplementation.addNewLesson(1, 1, 2, "Coding Lesson 1", 45, "coding");
        Lesson theoryLesson2 = courseManagerImplementation.addNewLesson(1, 1, 3, "Theory Lesson 2", 20, "theory");

        List<Lesson> longestLessons = courseManagerImplementation.getLongestLesson(1);

        assertEquals(1, longestLessons.size());
        assertEquals(45, longestLessons.get(0).getDuration());
    }

}
