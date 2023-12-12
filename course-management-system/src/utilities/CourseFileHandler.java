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
    public void writeCourse(Course course, String title, String extension) {
        String filename = title + extension;
        Path rootDirectory = Paths.get(".");
        String subdirectory = "courses";
        Path directoryPath = rootDirectory.resolve(subdirectory);

        // Check if the directory exists; create it if it doesn't
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path filePath = directoryPath.resolve(filename);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("Course %d - %s".formatted(course.getCourseId(), course.getTitle()));
            writer.newLine();
            for (Section section : course.getSections()) {
                writer.write("\tSection %d - %s".formatted(section.getSectionId(), section.getTitle()));
                writer.newLine();
                for (Lesson lesson : section.getLessons()) {
                    writer.write("\t\tLesson %d - %s (%d min) (%s)".formatted(lesson.getLessonId(),
                            lesson.getTitle(), lesson.getDuration(), lesson.getType()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCourse(String fileReadOption) {
        Path rootDirectory = Path.of(".");
        String subdirectoryName = "courses";
        Path courseDirectoryPath = rootDirectory.resolve(subdirectoryName);
        try {
            List<Path> pathsOfFiles = listCourseFiles(courseDirectoryPath);
            List<String> lines = new ArrayList<>();

            if (fileReadOption.equalsIgnoreCase("all")) {
                for (Path p : pathsOfFiles) {
                    lines = Files.readAllLines(p);
                    System.out.println("Read course from File : " + p.getFileName());
                    lines.forEach(System.out::println);
                    System.out.println();
                }
            } else if (Character.isDigit(fileReadOption.charAt(0))) {
                int intOption = Integer.parseInt(fileReadOption);
                lines = Files.readAllLines(pathsOfFiles.get(intOption - 1));
                System.out.println("Read course from File : " + pathsOfFiles.get(intOption - 1).getFileName());
                lines.forEach(System.out::println);
            } else {
                lines = Files.readAllLines(Path.of(fileReadOption));
                lines.forEach(System.out::println);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Path> listCourseFiles(Path path) {
        try (var paths = Files.newDirectoryStream(path, "*.txt")) {
            List<Path> pathsOfFiles = new ArrayList<>();
            paths.forEach(pathsOfFiles::add);
            return pathsOfFiles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeCourseFile(Path path) {
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return false;
        }
    }
}
