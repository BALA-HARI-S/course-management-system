package services;

import entities.Course;
import entities.Lesson;
import entities.Section;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.List;

public interface CourseManager {

    // LOAD SAMPLE COURSES

    /**
     * Load sample data for Course One.
     */
    void loadSampleDataOne();

    /**
     * Load sample data for Course Two.
     */
    void loadSampleDataTwo();

    // COURSE OPERATIONS

    /**
     * Create a new course.
     *
     * @param courseId      ID of the course.
     * @param title         title of the course.
     * @param authorName    author's name.
     * @param datePublished date the course was published.
     * @param cost          cost of the course.
     * @return The created course.
     */
    Course createCourse(int courseId, String title, String authorName,
                        String datePublished, double cost);

    /**
     * Get a course by its ID.
     *
     * @param courseId ID of the course.
     * @return The course with the specified ID.
     */
    Course getCourse(int courseId);

    /**
     * Remove a course by its ID.
     *
     * @param courseId The ID of the course to be removed.
     * @return True if the course is successfully removed, false otherwise.
     */
    boolean removeCourse(int courseId);

    /**
     * Remove a course file by its path.
     *
     * @param path The path of the course file to remove.
     * @return True if the file is successfully removed, false otherwise.
     */
    boolean removeCourseFile(Path path);

    /**
     * Get a list of all courses.
     *
     * @return List of all courses.
     */
    List<Course> getCourses();

    /**
     * Get a list of paths for all course files.
     *
     * @param path The path of courses directory.
     * @return List of paths for course files.
     */
    List<Path> getListOfCourseFiles(Path path);

    /**
     * Get the total number of courses.
     *
     * @return The total number of courses.
     */
    int getCourseCount();

    /**
     * Get the total number of course files.
     *
     * @return The total number of course files.
     */
    int getCourseFilesCount();

    /**
     * Edit the name of a course.
     *
     * @param courseId The ID of the course to be edited.
     * @param title    The new title for the course.
     */
    void editCourseName(int courseId, String title);

    /**
     * Retrieve course information as a formatted string.
     *
     * @param course The course to retrieve information from.
     * @return Formatted string containing course information.
     */
    String retrieveCourse(Course course);

    /**
     * Write a course to a file.
     *
     * @param course The course to be written to the file.
     * @throws FileAlreadyExistsException If the file already exists.
     */
    void writeCourseToFile(Course course) throws FileAlreadyExistsException;

    /**
     * Handle the case when a file already exists for a course.
     *
     * @param course The course for which the file already exists.
     */
    void handleFileAlreadyExists(Course course);

    /**
     * Read course information from a file.
     *
     * @param fileReadOption The file read option ("all", numeric option, or file path).
     */
    void readCourseFromFile(String fileReadOption);

    // SECTION OPERATIONS

    /**
     * Adds a new section to the specified course.
     *
     * @param courseId  The ID of the course to which the section will be added.
     * @param sectionId The ID of the new section.
     * @param title     The title of the new section.
     * @return The created Section object.
     */
    Section addNewSection(int courseId, int sectionId, String title);

    /**
     * Removes a section from the specified course.
     *
     * @param courseId  The ID of the course from which the section will be removed.
     * @param sectionId The ID of the section to be removed.
     * @return True if the section is successfully removed, false otherwise.
     */
    boolean removeSection(int courseId, int sectionId);

    /**
     * Retrieves a specific section from the specified course.
     *
     * @param courseId  The ID of the course containing the section.
     * @param sectionId The ID of the section to be retrieved.
     * @return The Section object with the specified ID.
     */
    Section getSection(int courseId, int sectionId);

    /**
     * Edits the name of a section in the specified course.
     *
     * @param courseId  The ID of the course containing the section.
     * @param sectionId The ID of the section to be edited.
     * @param title     The new title for the section.
     */
    void editSectionName(int courseId, int sectionId, String title);

    /**
     * Retrieves a list of all sections in the specified course.
     *
     * @param courseId The ID of the course.
     * @return List of Section objects in the specified course.
     */
    List<Section> getSections(int courseId);

    /**
     * Gets the count of sections in the specified course.
     *
     * @param courseId The ID of the course.
     * @return The number of sections in the specified course.
     */
    int getSectionsCount(int courseId);

    /**
     * Retrieves the shortest section in terms of total lesson duration.
     *
     * @param courseId The ID of the course.
     * @return The Section object representing the shortest section.
     */
    Section getShortestSection(int courseId);

    /**
     * Retrieves the longest section in terms of total lesson duration.
     *
     * @param courseId The ID of the course.
     * @return The Section object representing the longest section.
     */
    Section getLongestSection(int courseId);

    /**
     * Retrieves the longest lesson from each section in the specified course.
     *
     * @param courseId The ID of the course.
     * @return List of Lesson objects representing the longest lesson in each section.
     */
    List<Lesson> getLongestLesson(int courseId);

    /**
     * Retrieves the section with the most lessons in the specified course.
     *
     * @param courseId The ID of the course.
     * @return The Section object with the most lessons.
     */
    Section getSectionWithMostLessons(int courseId);

    /**
     * Retrieves the section with the most coding lessons in the specified course.
     *
     * @param courseId The ID of the course.
     * @return The Section object with the most coding lessons.
     */
    Section getSectionWithMostCodingLessons(int courseId);

    /**
     * Retrieves the section with the most theory lessons in the specified course.
     *
     * @param courseId The ID of the course.
     * @return The Section object with the most theory lessons.
     */
    Section getSectionWithMostTheoryLessons(int courseId);


    // LESSON OPERATIONS

    /**
     * Adds a new lesson to the specified section of the course.
     *
     * @param courseId  The ID of the course.
     * @param sectionId The ID of the section to which the lesson will be added.
     * @param lessonId  The ID of the new lesson.
     * @param title     The title of the new lesson.
     * @param duration  The duration of the new lesson in minutes.
     * @param type      The type of the new lesson (THEORY or CODING).
     * @return The created Lesson object.
     */
    Lesson addNewLesson(int courseId, int sectionId, int lessonId, String title, int duration, String type);

    /**
     * Removes a lesson from the specified section of the course.
     *
     * @param courseId  The ID of the course.
     * @param sectionId The ID of the section from which the lesson will be removed.
     * @param lessonId  The ID of the lesson to be removed.
     * @return True if the lesson is successfully removed, false otherwise.
     */
    boolean removeLesson(int courseId, int sectionId, int lessonId);

    /**
     * Retrieves a specific lesson from the specified section of the course.
     *
     * @param courseId  The ID of the course.
     * @param sectionId The ID of the section containing the lesson.
     * @param lessonId  The ID of the lesson to be retrieved.
     * @return The Lesson object with the specified ID.
     */
    Lesson getLesson(int courseId, int sectionId, int lessonId);

    /**
     * Edits the title of a lesson in the specified section of the course.
     *
     * @param courseId  The ID of the course.
     * @param sectionId The ID of the section containing the lesson.
     * @param lessonId  The ID of the lesson to be edited.
     * @param newTitle  The new title for the lesson.
     */
    void editLessonName(int courseId, int sectionId, int lessonId, String newTitle);

    /**
     * Retrieves a list of all lessons in the specified course.
     *
     * @param courseId The ID of the course.
     * @return List of Lesson objects in the specified course.
     */
    List<Lesson> getListOfAllLessons(int courseId);

    /**
     * Retrieves a list of lessons in the specified section of the course.
     *
     * @param courseId  The ID of the course.
     * @param sectionId The ID of the section.
     * @return List of Lesson objects in the specified section.
     */
    List<Lesson> getLessons(int courseId, int sectionId);

    /**
     * Retrieves a list of lessons in the specified course that contains a keyword in their title.
     *
     * @param courseId The ID of the course.
     * @param keyword  The keyword to search for in lesson titles.
     * @return List of Lesson objects with titles containing the keyword.
     */
    List<Lesson> getLessonsWithSameKeyword(int courseId, String keyword);

    /**
     * Gets the total count of lessons in the specified course.
     *
     * @param courseId The ID of the course.
     * @return The total number of lessons in the specified course.
     */
    int getLessonsCount(int courseId);


}
