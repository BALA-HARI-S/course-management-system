package services;

import entities.Course;
import entities.Lesson;
import entities.Section;
import utilities.CourseFileHandler;

import java.nio.file.Path;
import java.util.ArrayList;
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
        Section section2 = new Section(1, "IntelliJ Setup");
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
        Course webDevelopment = createNewCourse(2, "Web Development for front-end developers", "Tim", "29/12/23",
                200.00, 4.5, "y");
        Lesson lesson1 = new Lesson(1, "Introduction to the course", 2);
        Lesson lesson2 = new Lesson(2, "Remaster in progress", 10);
        Section section2 = new Section(1, "VS code Setup");
        Lesson lesson3 = new Lesson(3, "IntelliJ Installation", 2);
        Lesson lesson4 = new Lesson(4, "Remaster in progress", 3);
        Section section1 = new Section(1, "Course Introduction");
        webDevelopment.addSection(section1);
        section1.addLesson(lesson1);
        section1.addLesson(lesson2);
        webDevelopment.addSection(section2);
        section2.addLesson(lesson3);
        section2.addLesson(lesson4);
        writeCourseToFile(webDevelopment);
    }

    public List<Course> getCourseList() {
        return courses;
    }

    public List<Path> getListOfCourseFiles(Path path) {
        return fileHandler.listCourseFiles(path);
    }

    public Course createNewCourse(int id, String title, String authorName,
                                  String datePublished, double cost, double rating, String writeOption) {
        Course course = new Course(id, title, authorName,
                datePublished, cost, rating);
        getCourseList().add(course);
        if (writeOption.toLowerCase().charAt(0) == 'y') {
            writeCourseToFile(course);
        }
        System.out.println("Course successfully created");
        return course;
    }

    public void removeCourseFromList(int courseID) {
        for (Course course : getCourseList()) {
            if (courseID == course.getID()) {
                getCourseList().remove(courseID);
                return;
            }
        }
    }

    public boolean removeCourseFile(Path path) {
        return fileHandler.removeCourseFile(path);
    }

    public Path writeCourseToFile(Course course) {
        Path coursePath = fileHandler.writeCourse(course);
        return coursePath;
    }

    public void readCourseFromFile(String fileReadOption) {
        fileHandler.readCourse(fileReadOption);
    }


    private void addSampleLesson(int sectionID) {
        Section section = getCourseList().get(0).getSections().get(sectionID - 1);
        section.getLessons().add(new Lesson(1, "Sample lesson", 4));
    }

    private void addSection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Section Number : ");
        int sectionNo = scanner.nextInt();
        System.out.print("Section Title : ");
        String title = scanner.nextLine();
        Section section = new Section(sectionNo, title);
        getCourseList().get(0).addSection(section);
        addSampleLesson(section.getID());
        System.out.println("New section added successfully\n");
        scanner.close();
    }


    private void addLesson() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Lesson Number : ");
        int lessonNo = scanner.nextInt();

        System.out.print("Lesson Title : ");
        String title = scanner.nextLine();

        System.out.print("Lesson Duration : ");
        int duration = scanner.nextInt();

        System.out.println("\nList of Sections");

        // List sections to choose one from to add lesson
        for (Section section : getCourseList().get(0).getSections()) {
            System.out.println("\t" + section);
        }

        System.out.print("Select a section(No) : ");
        int sectionID = scanner.nextInt();
        Section section = getCourseList().get(0).getSections().get(sectionID - 1);
        section.getLessons().add(new Lesson(lessonNo, title, duration));
        System.out.println("New lesson added successfully\n");
        scanner.close();
    }

    private void removeLesson() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select lesson(ID): ");
        int id = scanner.nextInt();
        for (Section section : getCourseList().get(0).getSections()) {
            for (Lesson lesson : section.getLessons()) {
                if (lesson.getID() == id) {
                    section.getLessons().remove(lesson);
                }
            }

        }
        scanner.close();
    }


}
