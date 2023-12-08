import entities.Course;
import services.CourseManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

            final Path path1 = Paths.get(".");
            final List<Path> pathsOfFiles = manager.getListOfCourseFiles(path1);
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
                    int id = 1;
                    for (Path filePath : pathsOfFiles) {
                        System.out.printf("\t%d %s%n", id, filePath.getFileName());
                        id++;
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
                case 4 -> {
                    System.out.println("Operation : Remove course from list");
                    manager.getCourseList().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course to remove from list : ");
                    int i = scanner.nextInt();
                    manager.removeCourseFromList(i - 1);
                }
                case 5 -> {
                    System.out.println("Operation : Remove course file");
                    int id = 1;
                    for (Path filePath : pathsOfFiles) {
                        System.out.printf("\t%d %s%n", id, filePath.getFileName());
                        id++;
                    }
                    System.out.println("Which course file do you want delete?(No) : ");
                    int i = scanner.nextInt();
                    System.out.println(manager.removeCourseFile(pathsOfFiles.get(i - 1)) ?
                            "Course file successfully removed!" : "There was problem occurred when removing course file!");
                }
                case 6 -> {
                    System.out.println("Operation : Edit course name");
                    manager.getCourseList().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course from list to change it's name : ");
                    int i = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new name for the course : ");
                    String title = scanner.nextLine();
                    manager.getCourseList().get(i - 1).setTitle(title);
                }
                case 7 -> {
                    System.out.println("Operation : List available courses");
                    int courseId = 1;
                    for (Course course : manager.getCourseList()) {
                        System.out.printf("\t%d %s%n", courseId, course.getTitle());
                        courseId++;
                    }
                }
                case 8 -> {
                    System.out.println("Operation : Total number of courses");
                    System.out.printf("Number of Courses in List : %d%nNumber of Course Files: %d",
                            manager.getCourseList().size(), manager.getListOfCourseFiles(path1).size());
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
                4) Remove course from list
                5) Remove course file
                6) Edit Course name
                7) List courses
                8) Total number of course
                                
                SECTION OPERATIONS
                9) Add Section
                10) Remove Section
                11) Edit Section name
                Press 0 to exit()
                """;
        System.out.println(textBlock);
    }

}