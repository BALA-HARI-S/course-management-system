import entities.Course;
import entities.Section;
import services.CourseManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CourseManager manager = new CourseManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // load sample data
        manager.loadSampleDataOne();
        manager.loadSampleDataTwo();


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
                    manager.getListOfCourses().forEach(c -> System.out.printf("\tCourse %d %s%n", c.getID(), c.getTitle()));
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
                        System.out.printf("\tCourse %d %s%n", id, filePath.getFileName());
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
                    manager.getListOfCourses().forEach(c -> System.out.printf("\tCourse %d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course to remove from list : ");
                    int i = scanner.nextInt();
                    manager.removeCourseFromList(i - 1);
                }
                case 5 -> {
                    System.out.println("Course Operation : Remove course file");
                    int id = 1;
                    for (Path filePath : pathsOfFiles) {
                        System.out.printf("\tCourse %d %s%n", id, filePath.getFileName());
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
                    manager.getListOfCourses().forEach(c -> System.out.printf("\tCourse %d %s%n", c.getID(), c.getTitle()));
                    System.out.print("\nChoose course from list to change it's name : ");
                    int i = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new name for the course : ");
                    String title = scanner.nextLine();
                    manager.editCourseName(i, title);
                    System.out.println("Course name successfully changed to " + title);
                }
                case 7 -> {
                    System.out.println("Course Operation : List available courses");
                    int courseId = 1;
                    for (Course course : manager.getListOfCourses()) {
                        System.out.printf("\tCourse %d %s%n", courseId, course.getTitle());
                        courseId++;
                    }
                }
                case 8 -> {
                    System.out.println("Course Operation : Total number of courses");
                    System.out.printf("Number of Courses in List : %d%nNumber of Course Files: %d",
                            manager.getCourseCount(), manager.getCourseFilesCount());
                }
                case 9 -> {
                    System.out.println("Section Operation : Add new section to course");
                    System.out.println("Section ID : ");
                    int sectionID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Section Title : ");
                    String sectionTitle = scanner.nextLine();
                    int i = getCourseID("Choose course from list to add section : ");
                    scanner.nextLine();
                    manager.addSection(i, sectionID, sectionTitle);
                }
                case 10 -> {
                    System.out.println("Section Operation : Remove section from course");
                    int courseID = getCourseID("From which course do you want to remove section? Course(No.) : ");
                    System.out.println("Sections available in " + manager.getListOfCourses().get(courseID - 1).getTitle());
                    int sectionId = 1;
                    for (Section section : manager.getListOfSections(courseID)) {
                        System.out.printf("\tSection %d %s%n", sectionId, section.getTitle());
                        sectionId++;
                    }
                    System.out.println("Select section(No.) to remove : ");
                    int sectionID = scanner.nextInt();
                    manager.removeSection(courseID, sectionID);
                    System.out.println("Section successfully removed from course. Available sections");

                    for (Section section : manager.getListOfSections(courseID)) {
                        System.out.printf("\tSection %d %s%n", section.getID(), section.getTitle());
                    }
                }
                case 11 -> {
                    System.out.println("Section Operation : Edit section name");
                    int courseID = getCourseID("From which course do you want to rename section? Course(No.) : ");
                    System.out.println("Sections available in " + manager.getListOfCourses().get(courseID - 1).getTitle());
                    int sectionId = 1;
                    for (Section section : manager.getListOfSections(courseID)) {
                        System.out.printf("\tSection %d %s%n", sectionId, section.getTitle());
                        sectionId++;
                    }
                    System.out.println("Select section(No.) to rename : ");
                    int sectionID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter new section name : ");
                    String title = scanner.nextLine();
                    manager.editSectionName(courseID, sectionID, title);
                    System.out.println("Course name successfully changed to " + title);

                }
                case 12 -> {
                    System.out.println("Section Operation : List available sections");
                    int courseNo = getCourseID("List sections from course(No.) : ");
                    scanner.nextLine();
                    int sectionId = 1;
                    for (Section section : manager.getListOfSections(courseNo)) {
                        System.out.printf("\tSection %d %s%n", sectionId, section.getTitle());
                        sectionId++;
                    }
                }
                case 13 -> {
                    System.out.println("Section Operation : Total number of sections in a course");
                    int courseID = getCourseID("From which course do you want to count sections? Course(No.) : ");
                    System.out.printf("Total number of sections in %s is : %d",
                            manager.getListOfCourses().get(courseID - 1).getTitle(), manager.getSectionsCount(courseID));
                }
                case 17 -> {
                    System.out.println("Section operation : Sections with most lessons");
                    var section = manager.getSectionWithMostLessons(getCourseID("From which course do you want to know section with most lessons ? Course(No.) : "));
                    System.out.printf("'Section %d - %s' is the section with most lessons of count : %d%n",
                            section.getID(), section.getTitle(), section.getLessons().size());
                }
                case 18 -> {
                    System.out.println("Lesson Operation : Add new lesson");
                    System.out.println("Lesson ID : ");
                    int lessonID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Lesson Title : ");
                    String sectionTitle = scanner.nextLine();
                    System.out.println("Lesson duration : ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Choose course and section to add a lesson");
                    int courseNo = getCourseID("Choose course(No.) to add a lesson : ");
                    scanner.nextLine();

                    int sectionId = 1;
                    for (Section section : manager.getListOfSections(courseNo)) {
                        System.out.printf("\tSection %d %s%n", sectionId, section.getTitle());
                        sectionId++;
                    }
                    System.out.print("\nChoose section(No.) to add a lesson : ");
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
                12) List Sections
                13) Total number of sections
                14) Shortest section based on its Lesson's duration
                15) Longest section based on its Lesson's duration
                16) Longest lesson in each section based on its Lesson's duration
                17) Section with most lessons
                                
                LESSON OPERATIONS
                18) Add lesson
                19) Remove lesson
                20) Edit lesson name
                21) List lessons
                22) Lessons with same keyword
                23) Total number of lessons
                Press 0 to exit()
                """;
        System.out.println(textBlock);
    }

    private static int getCourseID(String message) {
        int id = 1;
        for (Course course : manager.getListOfCourses()) {
            System.out.printf("\tCourse %d %s%n", id, course.getTitle());
            id++;
        }
        System.out.print("\nFrom which course do you want to know section with most lessons ? Course(No.) : ");
        int courseID = scanner.nextInt();
        return courseID;
    }
}