package services;

import entities.Course;
import entities.Lesson;
import entities.Section;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.List;

public interface CourseManager {

    // LOAD SAMPLE COURSES

    void loadSampleDataOne();

    void loadSampleDataTwo();

    // COURSE OPERATIONS

    Course createCourse(int courseId, String title, String authorName,
                        String datePublished, double cost);

    Course getCourse(int courseId);

    boolean removeCourse(int courseId);

    boolean removeCourseFile(Path path);

    List<Course> getCourses();

    List<Path> getListOfCourseFiles(Path path);

    int getCourseCount();

    int getCourseFilesCount();

    void editCourseName(int courseId, String title);

    void printCourse(Course course);

    void writeCourseToFile(Course course) throws FileAlreadyExistsException;

    void readCourseFromFile(String fileReadOption);

    // SECTION OPERATIONS
    Section addNewSection(int courseId, int sectionId, String title);

    boolean removeSection(int courseId, int sectionId);

    Section getSection(int courseId, int sectionId);

    void editSectionName(int courseId, int sectionId, String title);

    List<Section> getSections(int courseId);

    int getSectionsCount(int courseId);

    Section getShortestSection(int courseId);

    Section getLongestSection(int courseId);

    List<Lesson> getLongestLesson(int courseId);

    Section getSectionWithMostLessons(int courseId);


    // LESSON OPERATIONS
    Lesson addNewLesson(int courseId, int sectionId, int lessonId, String title, int duration, String type);

    boolean removeLesson(int courseId, int sectionId, int lessonId);

    Lesson getLesson(int courseId, int sectionId, int lessonId);

    void editLessonName(int courseId, int sectionId, int lessonId, String newTitle);

    List<Lesson> getListOfAllLessons(int courseId);

    List<Lesson> getLessons(int courseId, int sectionId);

    List<Lesson> getLessonsWithSameKeyword(int courseId, String keyword);

    int getLessonsCount(int courseId);


}
