package utilities;

import entities.Course;
import entities.Lesson;
import entities.Section;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CourseFileHandler {
    public void writeCourse(Course course){
        String filename = course.getTitle()+".txt";
        Path path = Paths.get(filename);
        if(!Files.exists(path)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)){
            writer.write("Course " + course.getTitle());
            writer.newLine();
            for (Section section: course.getSections()) {
                writer.write("\tSection " + section.getID() + " - " +section.getTitle());
                writer.newLine();
                for (Lesson lesson: section.getLessons()) {
                    writer.write("\t\tLesson " + lesson.getID() + " - " +lesson.getTitle());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
