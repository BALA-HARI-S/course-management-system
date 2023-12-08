import entities.Course;
import entities.Section;
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
                    System.out.println("Course Operation : writing course to file");
                    manager.getListOfCourses().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course to write : ");
                    int i = scanner.nextInt();
                    var course = manager.writeCourseToFile(manager.getListOfCourses().get(i - 1));
                    System.out.println("Course successfully written to file");
                    manager.readCourseFromFile("" + course.getID());
                }
                case 2 -> {
                    System.out.println("Course Operation : Read course from file");
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
                    System.out.println("Course Operation : Creating New Course");
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
                    System.out.println("Course Operation : Remove course from list");
                    manager.getListOfCourses().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course to remove from list : ");
                    int i = scanner.nextInt();
                    manager.removeCourseFromList(i - 1);
                }
                case 5 -> {
                    System.out.println("Course Operation : Remove course file");
                    int id = 1;
                    for (Path filePath : pathsOfFiles) {
                        System.out.printf("\t%d %s%n", id, filePath.getFileName());
                        id++;
                    }
                    System.out.println("Which course file do you want delete?(No) : ");
                    int i = scanner.nextInt();
                    if (i > 0 && i < manager.getListOfCourseFiles(path1).size()) {
                        boolean removeCourse = manager.removeCourseFile(pathsOfFiles.get(i - 1));
                        System.out.println(removeCourse ?
                                "Course file successfully removed!" : "There was problem occurred when removing course file!");
                        return;
                    }
                    System.out.println("Invalid file number, Please select correct file number from the list.");

                }
                case 6 -> {
                    System.out.println("Course Operation : Edit course name");
                    manager.getListOfCourses().forEach(c -> System.out.printf("\t%d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course from list to change it's name : ");
                    int i = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new name for the course : ");
                    String title = scanner.nextLine();
                    manager.getListOfCourses().get(i - 1).setTitle(title);
                }
                case 7 -> {
                    System.out.println("Course Operation : List available courses");
                    int courseId = 1;
                    for (Course course : manager.getListOfCourses()) {
                        System.out.printf("\t%d %s%n", courseId, course.getTitle());
                        courseId++;
                    }
                }
                case 8 -> {
                    System.out.println("Course Operation : Total number of courses");
                    System.out.printf("Number of Courses in List : %d%nNumber of Course Files: %d",
                            manager.getListOfCourses().size(), manager.getListOfCourseFiles(path1).size());
                }
                case 9 -> {
                    System.out.println("Section Operation : Add new section to course");
                    System.out.println("Section ID : ");
                    int sectionID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Section Title : ");
                    String sectionTitle = scanner.nextLine();

                    int courseId = 1;
                    for (Course course : manager.getListOfCourses()) {
                        System.out.printf("\t%d %s%n", courseId, course.getTitle());
                        courseId++;
                    }

                    System.out.print("\nChoose course from list to add section : ");
                    int i = scanner.nextInt();
                    scanner.nextLine();
                    manager.addSection(i, sectionID, sectionTitle);
                }
                case 13 -> {
                    System.out.println("Section Operation : List available sections");
                    int courseId = 1;
                    for (Course course : manager.getListOfCourses()) {
                        System.out.printf("\t%d %s%n", courseId, course.getTitle());
                        courseId++;
                    }
                    System.out.print("\nChoose course(No.) from list : ");
                    int courseNo = scanner.nextInt();
                    scanner.nextLine();

                    int sectionId = 1;
                    for (Section section : manager.getListOfSections(courseNo)) {
                        System.out.printf("\t%d %s%n", sectionId, section.getTitle());
                        sectionId++;
                    }
                }
                case 19 -> {
                    System.out.println("Lesson Operation : Add new lesson");
                    System.out.println("Lesson ID : ");
                    int lessonID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Lesson Title : ");
                    String sectionTitle = scanner.nextLine();
                    System.out.println("Lesson duration : ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("\nChoose course and section to add lesson");
                    int courseId = 1;
                    for (Course course : manager.getListOfCourses()) {
                        System.out.printf("\t%d %s%n", courseId, course.getTitle());
                        courseId++;
                    }
                    System.out.print("\nChoose course(No.) from list : ");
                    int courseNo = scanner.nextInt();
                    scanner.nextLine();

                    int sectionId = 1;
                    for (Section section : manager.getListOfSections(courseNo)) {
                        System.out.printf("\t%d %s%n", sectionId, section.getTitle());
                        sectionId++;
                    }
                    System.out.print("\nChoose section(No.) from list : ");
                    int sectionNo = scanner.nextInt();
                    scanner.nextLine();
                    manager.addLesson(courseNo, sectionNo, lessonID, sectionTitle, duration);
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
                8) Total number of courses
                                
                SECTION OPERATIONS
                9) Add Section
                10) Remove Section
                11) Edit Section name
                13) List Sections
                14) Total number of sections
                15) Shortest section based on duration
                16) Longest section based on duration
                17) Longest lesson in each section
                18) Section with most lessons
                                
                LESSON OPERATIONS
                19) Add lesson
                20) Remove lesson
                21) Edit lesson name
                22) List lessons
                23) Lessons with same keyword
                24) Total number of lessons
                Press 0 to exit()
                """;
        System.out.println(textBlock);
    }

}