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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            System.out.println("Course successfully write to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCourse(){
        Path path = Paths.get(".");
        Scanner scanner = new Scanner(System.in);
        try (var theFiles = Files.newDirectoryStream(path, "*.txt")) {
            int id = 1;
            for(Path p: theFiles){
                System.out.print(id);
                System.out.print(p + "\n");
                id++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.print("\nWhich course do you want to read(No/ All): ");
        String option = scanner.nextLine();


            try (var theFiles = Files.newDirectoryStream(path, "*.txt")) {
                List<Path> filePaths = new ArrayList<>();
                theFiles.forEach(filePaths::add);
                if (option.toLowerCase().charAt(0) == 'a'){
                    for(Path p: filePaths){
                        List<String> lines = Files.readAllLines(p);
                        System.out.println("Read course from File : " + p);
                        lines.forEach(System.out::println);
                        System.out.println();
                    }

                } else {
                    int intOption = Integer.parseInt(option);
                    List<String> lines = Files.readAllLines(filePaths.get(intOption-1));
                    System.out.println("Read course from File : " + filePaths.get(intOption-1));
                    lines.forEach(System.out::println);
                    System.out.println();
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
