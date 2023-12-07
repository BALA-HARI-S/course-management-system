package services;

import entities.Course;
import entities.Lesson;
import entities.Section;
import utilities.CourseFileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseManager {
    private List<Course> courses = new ArrayList<>();
    private Course course;
    private Section section;
    private Lesson lesson;


    public void courseInitializer(){
        Scanner scanner = new Scanner(System.in);
        sampleCourse();
        boolean flag = true;

        while(flag){
            printCourse();
            printActions();
            System.out.print("How can I help you (Choose any option): ");
            int option = scanner.nextInt();

            switch (option){
                case 0 -> flag = false;
                case 1 -> addLesson();
                case 2 -> addSection();
                case 3 -> removeLesson();
                case 4 -> printCourse();
                default -> {
                    System.out.println("INVALID_VALUE");
                    flag = false;
                }
            }
        }

    }

    private void sampleCourse(){
        Course jvm = new Course(1,"Java Programming Masterclass for Software Developers","Tim", "29/12/23",
                200.00, 4.5);
        courses.add(jvm);
        Section section1 = new Section(1,"Course Introduction");
        Lesson lesson1 = new Lesson(1,"Introduction to the course", 2);
        Lesson lesson2 = new Lesson(2,"Remaster in progress", 10);
        Section section2 = new Section(1,"IntelliJ Setup");
        Lesson lesson3 = new Lesson(3,"IntelliJ Installation", 2);
        Lesson lesson4 = new Lesson(4,"Remaster in progress", 3);
        jvm.addSection(section1);
        section1.addLesson(lesson1);
        section1.addLesson(lesson2);
        jvm.addSection(section2);
        section2.addLesson(lesson3);
        section2.addLesson(lesson4);
        CourseFileHandler fileHandler = new CourseFileHandler();
        fileHandler.writeCourse(jvm);
    }

    private void printActions(){
        String textBlock = """
                1) addLesson
                2) addSection
                3) removeLesson
                4) removeSection
                5) editSectionName
                6) editLessonName
                7) sectionsList
                8) totalNumberOfSections
                9) sectionWithMostLessons (Section With Most NumberOfLesson)
                10) longestSection (Based on duration)
                11) shortestSection (Based on duration)
                12) lessonsList
                13) totalNumberOfLessons
                14) lessonsWithSameKeywords
                15) longestLessonInEachSection
                16) read course from a file
                17) write course into a file (pretty print)
                Exit (0)
                """;
        System.out.println(textBlock);
    }
    private void addLesson(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Lesson Number : ");
        int lessonNo = scanner.nextInt();

        System.out.print("Lesson Title : ");
        String title = scanner.nextLine();

        System.out.print("Lesson Duration : ");
        int duration = scanner.nextInt();

        System.out.println("\nList of Sections");

        // List sections to choose one from to add lesson
        for(Section section: courses.get(0).getSections()){
            System.out.println("\t" + section);
        }

        System.out.print("Select a section(No) : ");
        int sectionID = scanner.nextInt();
        Section section = courses.get(0).getSections().get(sectionID-1);
        section.getLessons().add(new Lesson(lessonNo,title, duration));
        System.out.println("New lesson added successfully\n");
        printCourse();
        scanner.close();
    }

    private void addSampleLesson(int sectionID){
        Section section = courses.get(0).getSections().get(sectionID-1);
        section.getLessons().add(new Lesson(1,"Sample lesson", 4));
    }

    private void addSection(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Section Number : ");
        int sectionNo = scanner.nextInt();
        System.out.print("Section Title : ");
        String title = scanner.nextLine();
        Section section = new Section(sectionNo,title);
        courses.get(0).addSection(section);
        addSampleLesson(section.getID());
        System.out.println("New section added successfully\n");
        printCourse();
        scanner.close();
    }


    private void removeLesson(){
        Scanner scanner = new Scanner(System.in);
        printCourse();
        System.out.print("Select lesson(ID): ");
        int id = scanner.nextInt();
        for(Section section: courses.get(0).getSections()){
            for(Lesson lesson: section.getLessons()){
                if (lesson.getID() == id){
                    section.getLessons().remove(lesson);
                }
            }

        }
        printCourse();
        scanner.close();
    }

    private void printCourse() {
        for(Course course: courses){
            System.out.println( "Course - " + course.getTitle());
            for(Section s: course.getSections()){
                System.out.println( "\t" + s);
                for(Lesson l:  s.getLessons()){
                    System.out.println( "\t\t" + l +
                            " (" + l.getDuration() + " mins)");
                }
            }
        }

    }
}
