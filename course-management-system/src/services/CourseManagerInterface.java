package services;

import entities.Course;
import entities.Lesson;
import entities.Section;

import java.nio.file.Path;
import java.util.List;

public interface CourseManagerInterface {

    // LOAD SAMPLE COURSES
    void loadSampleDataOne();

    void loadSampleDataTwo();

    // COURSE OPERATIONS
    Course writeCourseToFile(Course course);

    void readCourseFromFile(String fileReadOption);

    Course createNewCourse(int id, String title, String authorName,
                           String datePublished, double cost, double rating);

    void removeCourseFromList(int courseID);

    boolean removeCourseFile(Path path);

    List<Course> getListOfCourses();

    List<Path> getListOfCourseFiles(Path path);

    int getCourseCount();

    int getCourseFilesCount();

    void editCourseName(int courseID, String title);

    void printCourse(Course course);


    // SECTION OPERATIONS
    Section addNewSection(int courseID, int sectionID, String title);

    boolean removeSection(int courseID, int sectionID);

    void editSectionName(int courseID, int sectionID, String title);

    List<Section> getListOfSections(int courseID);

    int getSectionsCount(int courseID);

    Section getShortestSection(int courseID);

    Section getLongestSection(int courseID);

    List<Lesson> getLongestLesson(int courseID);

    Section getSectionWithMostLessons(int courseID);


    // LESSON OPERATIONS
    Lesson addNewLesson(int courseID, int sectionID, int lessonID, String title, int duration, String type);

    boolean removeLesson(int courseID, int sectionID, int lessonID);

    void editLessonName(int courseID, int sectionID, int lessonID, String newTitle);

    List<Lesson> getListOfAllLessons(int courseID);

    List<Lesson> getListOfLessonsFromSection(int courseID, int sectionID);

    List<Lesson> getLessonsWithSameKeyword(int courseID, String keyword);

    int getLessonsCount(int courseID);


}
