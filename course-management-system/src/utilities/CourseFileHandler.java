package utilities;

import entities.Course;
import entities.Lesson;
import entities.Section;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CourseFileHandler {
    public Path writeCourse(Course course) {
        String filename = course.getTitle() + ".txt";
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Course " + " - " + course.getTitle());
            writer.newLine();
            for (Section section : course.getSections()) {
                writer.write("\tSection " + section.getID() + " - " + section.getTitle());
                writer.newLine();
                for (Lesson lesson : section.getLessons()) {
                    writer.write("\t\tLesson " + lesson.getID() + " - " + lesson.getTitle());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public void readCourse(String fileReadOption) {
        Path path = Paths.get(".");
        try (var filesPath = Files.newDirectoryStream(path, "*.txt")) {

            List<Path> filePaths = new ArrayList<>();
            filesPath.forEach(filePaths::add);
            List<String> lines = new ArrayList<>();

            if (fileReadOption.equalsIgnoreCase("all")) {
                for (Path p : filePaths) {
                    lines = Files.readAllLines(p);
                    System.out.println("Read course from File : " + p.getFileName());
                    lines.forEach(System.out::println);
                }
            } else if (Character.isDigit(fileReadOption.charAt(0))) {
                int intOption = Integer.parseInt(fileReadOption);
                lines = Files.readAllLines(filePaths.get(intOption - 1));
                System.out.println("Read course from File : " + filePaths.get(intOption - 1).getFileName());
                lines.forEach(System.out::println);
            } else {
                lines = Files.readAllLines(Path.of(fileReadOption));
                lines.forEach(System.out::println);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
