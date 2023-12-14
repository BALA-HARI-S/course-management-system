package testing;

import entities.Course;
import entities.Lesson;
import entities.LessonType;
import entities.Section;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CourseManager;
import services.CourseManagerImplementation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseManagerImplementationTest {

    private CourseManager courseManager;

    @BeforeEach
    void setUp() {
        courseManager = new CourseManagerImplementation();
    }

    @Test
    void testCreateCourse() {
        Course course = courseManager.createCourse(1, "Test Course", "John Doe", "01-01-2023", 100.0);
        assertNotNull(course);
        assertEquals("Test Course", course.getTitle());
        assertEquals("John Doe", course.getAuthorName());
        assertEquals(100.0, course.getPrice());
    }

    @Test
    void testGetCourse() {
        Course course = courseManager.createCourse(1, "Test Course", "Test Name", "14-12-2023", 100.0);
        int courseId = course.getCourseId();
        Course retrievedCourse = courseManager.getCourse(courseId);
        assertEquals(course, retrievedCourse);
    }

    @Test
    void testRemoveCourse() {
        Course course = courseManager.createCourse(1, "Test Course", "Test Name", "01-01-2023", 100.0);
        int courseId = course.getCourseId();
        assertTrue(courseManager.removeCourse(courseId));
        assertThrows(NullPointerException.class, () -> courseManager.getCourse(courseId));
    }

    @Test
    void testAddNewSection() {
        Course course = courseManager.createCourse(1, "Test Course", "Test Name", "01-01-2023", 100.0);
        int courseId = course.getCourseId();
        Section section = courseManager.addNewSection(courseId, 1, "Section 1");
        assertNotNull(section);
        assertEquals("Section 1", section.getTitle());
        assertEquals(1, section.getSectionId());
    }

    @Test
    void testAddNewLesson() {
        Course course = courseManager.createCourse(1, "Test Course", "Test Name", "01-01-2023", 100.0);
        int courseId = course.getCourseId();
        Section section = courseManager.addNewSection(courseId, 1, "Section 1");
        Lesson lesson = courseManager.addNewLesson(courseId, section.getSectionId(), 1, "Lesson 1", 30, "theory");
        assertNotNull(lesson);
        assertEquals("Lesson 1", lesson.getTitle());
        assertEquals(30, lesson.getDuration());
        assertEquals(LessonType.THEORY, lesson.getType());
    }

    @Test
    void testGetSections() {
        Course course = courseManager.createCourse(1, "Test Course", "Test Name", "01-01-2023", 100.0);
        int courseId = course.getCourseId();
        Section section1 = courseManager.addNewSection(courseId, 1, "Section 1");
        Section section2 = courseManager.addNewSection(courseId, 2, "Section 2");
        List<Section> sections = courseManager.getSections(courseId);
        assertEquals(2, sections.size());
        assertTrue(sections.contains(section1));
        assertTrue(sections.contains(section2));
    }

}
