package services;

import entities.Course;
import entities.Lesson;
import entities.Section;
import utilities.CourseFileHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CourseManager {
    private final List<Course> courses = new ArrayList<>();
    private final CourseFileHandler fileHandler = new CourseFileHandler();

    public void loadSampleDataOne() {
        Course jvm = createNewCourse(1, "Java Programming Masterclass for Software Developers", "Tim", "29/12/23",
                200.00, 4.5, "y");
        Lesson lesson1 = new Lesson(1, "Introduction to the course", 2);
        Lesson lesson2 = new Lesson(2, "Remaster in progress", 10);
        Section section2 = new Section(2, "IntelliJ Setup");
        Lesson lesson3 = new Lesson(3, "IntelliJ Installation", 2);
        Lesson lesson4 = new Lesson(4, "Remaster in progress", 3);
        Section section1 = new Section(1, "Course Introduction");
        jvm.addSection(section1);
        section1.addLesson(lesson1);
        section1.addLesson(lesson2);
        jvm.addSection(section2);
        section2.addLesson(lesson3);
        section2.addLesson(lesson4);
        writeCourseToFile(jvm);
    }

    public void loadSampleDataTwo() {
        var webDevelopment = createNewCourse(2, "Web Development for front-end developers", "Tim", "29/12/23",
                200.00, 4.5, "y");
        var section1 = addSection(webDevelopment.getID(), 1, "Course Introduction");
        addLesson(webDevelopment.getID(), section1.getID(), 1, "Introduction to the course", 2);
        addLesson(webDevelopment.getID(), section1.getID(), 2, "Remaster in progress", 10);
        addLesson(webDevelopment.getID(), section1.getID(), 3, "Introduction to the HTML,CSS,JS", 2);
        addLesson(webDevelopment.getID(), section1.getID(), 4, "Remaster in progress", 10);
        var section2 = addSection(webDevelopment.getID(), 2, "VS code Setup");
        addLesson(webDevelopment.getID(), section2.getID(), 5, "VS code Installation", 2);
        addLesson(webDevelopment.getID(), section2.getID(), 6, "Remaster in progress", 3);
        writeCourseToFile(webDevelopment);
    }

    public Course writeCourseToFile(Course course) {
        fileHandler.writeCourse(course);
        return course;
    }

    public void readCourseFromFile(String fileReadOption) {
        fileHandler.readCourse(fileReadOption);
    }

    public Course createNewCourse(int id, String title, String authorName,
                                  String datePublished, double cost, double rating, String writeOption) {
        Course course = new Course(id, title, authorName,
                datePublished, cost, rating);
        getListOfCourses().add(course);
        if (writeOption.toLowerCase().charAt(0) == 'y') {
            writeCourseToFile(course);
        }
        System.out.println("Course successfully created");
        return course;
    }

    public void removeCourseFromList(int courseID) {
        for (Course course : getListOfCourses()) {
            if (courseID == course.getID()) {
                getListOfCourses().remove(courseID);
                return;
            }
        }
    }

    public boolean removeCourseFile(Path path) {
        return fileHandler.removeCourseFile(path);
    }

    public List<Course> getListOfCourses() {
        return courses;
    }

    public List<Path> getListOfCourseFiles(Path path) {
        return fileHandler.listCourseFiles(path);
    }

    public int getCourseCount() {
        return getListOfCourses().size();
    }

    public int getCourseFilesCount() {
        return getListOfCourseFiles(Paths.get(".")).size();
    }

    public void editCourseName(int courseID, String title) {
        var course = getListOfCourses().get(courseID - 1);
        course.setTitle(title);
    }

    public Section addSection(int courseID, int sectionID, String title) {
        var section = new Section(sectionID, title);
        getListOfCourses().get(courseID - 1).addSection(section);
        return section;
    }

    public void removeSection(int courseID, int sectionID) {
        var sections = getListOfCourses().get(courseID - 1).getSections();
        sections.remove(sectionID - 1);
    }

    public void editSectionName(int courseID, int sectionID, String title) {
        var section = getListOfCourses().get(courseID - 1).getSections().get(sectionID - 1);
        section.setTitle(title);
    }

    public List<Section> getListOfSections(int courseID) {
        return getListOfCourses().get(courseID - 1).getSections();
    }

    public int getSectionsCount(int courseID) {
        return getListOfCourses().get(courseID - 1).getSections().size();
    }

    public Section getSectionWithMostLessons(int courseID) {
        var sections = getListOfSections(courseID);
        sections.sort(new SectionLessonComparator().reversed());
        return sections.get(0);
    }

    public Lesson addLesson(int courseID, int sectionID,
                            int lessonID, String title, int duration) {
        var lesson = new Lesson(lessonID, title, duration);
        getListOfCourses().get(courseID - 1).getSections().get(sectionID - 1)
                .addLesson(lesson);
        return lesson;
    }

    private void removeLesson() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select lesson(ID): ");
        int id = scanner.nextInt();
        for (Section section : getListOfCourses().get(0).getSections()) {
            for (Lesson lesson : section.getLessons()) {
                if (lesson.getID() == id) {
                    section.getLessons().remove(lesson);
                }
            }

        }
        scanner.close();
    }


}

class SectionLessonComparator implements Comparator<Section> {
    @Override
    public int compare(Section section1, Section section2) {
        return Integer.compare(section1.getLessons().size(), section2.getLessons().size());
    }
}

