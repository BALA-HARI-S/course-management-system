package utilities;

import entities.Course;
import services.CourseManager;
import services.CourseManagerImplementation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CourseFileHandler {
    /**
     * Write course information to a file.
     *
     * @param course    The course to be written to the file.
     * @param title     The title of the file.
     * @param extension The file extension (e.g., ".txt").
     */
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
            CourseManager manger = new CourseManagerImplementation();
            writer.write(manger.retrieveCourse(course));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read course information from files based on the specified option.
     *
     * @param fileReadOption The file read option ("all", numeric option, or file path).
     */
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

    /**
     * List all course files in the specified directory with a ".txt" extension.
     *
     * @param path The path of the directory to search for course files.
     * @return List of paths for course files.
     */
    public List<Path> listCourseFiles(Path path) {
        try (var paths = Files.newDirectoryStream(path, "*.txt")) {
            List<Path> pathsOfFiles = new ArrayList<>();
            paths.forEach(pathsOfFiles::add);
            return pathsOfFiles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove the specified course file.
     *
     * @param path The path of the file to be removed.
     * @return True if the file is successfully removed, false otherwise.
     */
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
