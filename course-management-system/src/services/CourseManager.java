package services;

import entities.Course;
import entities.Lesson;
import entities.Section;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses = new ArrayList<>();
    private void courseInitializer(){
        Course jvm = new Course("Java Programming Masterclass for Software Developers","Tim", "29/12/23",
                200.00, 4.5);
        courses.add(jvm);
        Section section1 = new Section("Course Introduction");
        Lesson lesson1 = new Lesson("Introduction to the course", 2);
        Lesson lesson2 = new Lesson("Remaster in progress", 10);
        jvm.addSection(section1);
        section1.addLesson(lesson1);
        section1.addLesson(lesson2);
    }
    public void printCourse() {
        courseInitializer();
        for(Course course: courses){
            System.out.println("Course - " + course.getTitle());
            for(Section s: course.getSections()){
                System.out.println("\t" + s);
                for(Lesson l:  s.getLessons()){
                    System.out.println("\t\t" + l +
                            " (" + l.getDuration() + " mins)");
                }
            }
        }

    }
}
