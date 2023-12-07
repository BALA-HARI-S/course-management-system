import entities.Course;
import services.CourseManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // load sample data
        CourseManager manager = new CourseManager();
        manager.loadSampleDataOne();
        manager.loadSampleDataTwo();

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            printActions();
            System.out.print("How can I help you (Choose any option): ");
            int option = scanner.nextInt();

            switch (option) {
                case 0 -> flag = false;
                case 1 -> {
                    System.out.println("Operation : writing course to file");
                    manager.getCourseList().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course to write : ");
                    int i = scanner.nextInt();
                    manager.writeCourseToFile(manager.getCourseList().get(i - 1));
                }
                case 2 -> {
                    System.out.println("Operation : Read course from file");
                    Path path = Paths.get(".");
                    try (var filePaths = Files.newDirectoryStream(path, "*.txt")) {
                        int id = 1;
                        for (Path filePath : filePaths) {
                            System.out.printf("\t%d %s%n", id, filePath.getFileName());
                            id++;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    scanner.nextLine();
                    System.out.print("\nWhich course do you want to read : ");
                    String readOption = scanner.nextLine();
                    manager.readCourseFromFile(readOption);

                }
                case 3 -> {
                    System.out.println("Operation : Creating New Course");
                    System.out.println("Course ID : ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Course Name : ");
                    String title = scanner.nextLine();

                    System.out.println("Course Author's Name : ");
                    String authorName = scanner.nextLine();

                    System.out.println("Course Publish date : ");
                    String publishDate = scanner.nextLine();

                    System.out.println("Course Price : ");
                    double price = scanner.nextDouble();

                    System.out.println("Course Rating : ");
                    double rating = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Do you want to write this course to file(y/n): ");
                    String writeOption = scanner.nextLine();

                    Course course = manager.createNewCourse(id, title, authorName, publishDate, price, rating, writeOption);
                    System.out.println("New Course created ' " + course.getTitle() + " '");
                }
                case 6 -> {
                    System.out.println("Operation : List available courses");
                    manager.getCourseList().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                }
                default -> System.out.println("INVALID_VALUE, Choose correct option!!!");
            }

        }

    }

    private static void printActions() {
        String textBlock = """
                                
                COURSE OPERATIONS
                1) Write course to file
                2) Read course from file
                3) Add course
                4) Remove course
                5) Edit Course name
                6) List courses
                7) Total number of course
                                
                SECTION OPERATIONS
                8) Add Section
                9) Remove Section
                10) Edit Section name
                Press 0 to exit()
                """;
        System.out.println(textBlock);
    }

}