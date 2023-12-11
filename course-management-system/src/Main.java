import entities.Course;
import entities.Lesson;
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
                    System.out.printf("Existing courses : %d, Your course id should be %d or above.",
                            manager.getCourseCount(), 1 + manager.getCourseCount());
                    System.out.println("\nCourse ID : ");
                    int courseID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Course Name : ");
                    String courseTitle = scanner.nextLine();

                    System.out.println("Course Author's Name : ");
                    String authorName = scanner.nextLine();

                    System.out.println("Course Publish date : ");
                    String publishDate = scanner.nextLine();

                    System.out.println("Course Price : ");
                    double price = scanner.nextDouble();

                    System.out.println("Course Rating : ");
                    double rating = scanner.nextDouble();
                    scanner.nextLine();
                    var newCourse = manager.createNewCourse(courseID, courseTitle, authorName, publishDate, price, rating);
                    System.out.printf("Course created : Course %d - %s%n", newCourse.getID(), newCourse.getTitle());

                    System.out.println("Do you want to add sections?(y/n): ");
                    String addSectionOption = scanner.nextLine();
                    if (addSectionOption.toLowerCase().charAt(0) == 'y') {
                        System.out.println("Section Operation : Add new section to course");
                        System.out.println("Section ID : ");
                        int sectionID = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Section Title : ");
                        String sectionTitle = scanner.nextLine();
                        var section = manager.addNewSection(courseID, sectionID, sectionTitle);
                        System.out.printf("Section added : %d %s", section.getID(), section.getTitle());
                        System.out.println("\n1) Edit section name \n2) Remove this section \n3) Skip");
                        System.out.println("option : ");
                        int sectionOption = scanner.nextInt();
                        scanner.nextLine();
                        if (sectionOption == 1) {
                            System.out.println("Enter new title : ");
                            String newTitle = scanner.nextLine();
                            manager.editSectionName(courseID, section.getID(), newTitle);
                            System.out.println("Section name successfully changed to " + section.getTitle());
                        }
                        if (sectionOption == 2) {
                            manager.removeSection(courseID, section.getID());
                            System.out.println("section successfully removed");
                        }

                        System.out.println("Do you want to add lesson?(y/n): ");
                        String addLessonOption = scanner.nextLine();
                        if (addLessonOption.toLowerCase().charAt(0) == 'y') {
                            System.out.println("Lesson Operation : Add new lesson");
                            System.out.println("Lesson ID : ");
                            int lessonID = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Lesson Title : ");
                            String lessonTitle = scanner.nextLine();
                            System.out.println("Lesson duration : ");
                            int duration = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Lesson Type (Theory/Coding) : ");
                            String lessonType = scanner.nextLine();
                            var lesson = manager.addNewLesson(courseID, section.getID(), lessonID, lessonTitle, duration, lessonType);
                            System.out.printf("Lesson added : %d %s", lesson.getID(), lesson.getTitle());
                        }
                    }
                    System.out.println("\nNew Course successfully created");
                    manager.printCourse(newCourse);
                    System.out.println("\nDo you want to write this course to file?(y/n): ");
                    String writeOption = scanner.nextLine();
                    if (writeOption.toLowerCase().charAt(0) == 'y') {
                        manager.writeCourseToFile(newCourse);
                        System.out.println("New Course successfully written to file");
                    }

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
                    manager.addNewSection(i, sectionID, sectionTitle);
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
                    int courseID = getCourseID("List sections from course(No.) : ");
                    scanner.nextLine();
                    int id = 1;
                    for (Section section : manager.getListOfSections(courseID)) {
                        System.out.printf("\tSection %d %s%n", id, section.getTitle());
                        id++;
                    }
                }
                case 13 -> {
                    System.out.println("Section Operation : Total number of sections in a course");
                    int courseID = getCourseID("From which course do you want to count sections? Course(No.) : ");
                    System.out.printf("Total number of sections in %s is : %d",
                            manager.getListOfCourses().get(courseID - 1).getTitle(), manager.getSectionsCount(courseID));
                }
                case 14 -> {
                    System.out.println("Section Operation : Shortest section based on lesson duration");
                    System.out.println("Choose course to filter shortest section");
                    int courseID = getCourseID("Choose course(No.) : ");
                    var section = manager.getShortestSection(courseID);
                    System.out.println("Shortest section in Course : " + manager.getListOfCourses().get(courseID).getTitle());
                    System.out.printf("\tSection %d - %s", section.getID(), section.getTitle());
                }
                case 15 -> {
                    System.out.println("Section Operation : Longest section based on lesson duration");
                    System.out.println("Choose course to filter Longest section");
                    int courseID = getCourseID("Choose course(No.) : ");
                    var section = manager.getLongestSection(courseID);
                    System.out.println("Shortest section in Course : " + manager.getListOfCourses().get(courseID).getTitle());
                    System.out.printf("\tSection %d - %s", section.getID(), section.getTitle());
                }
                case 16 -> {
                    System.out.println("Section Operation : Longest lesson in each section based on lesson duration");
                    System.out.println("Choose course to list longest lesson in each section");
                    int courseID = getCourseID("Choose course(No.) to list lesson : ");
                    System.out.println("Longest lessons in each section of this course : ");
                    var lessons = manager.getLongestLesson(courseID);
                    int id = 1;
                    for (Lesson lesson : lessons) {
                        System.out.printf("\tSection %d : Lesson %d - %s (%d mins)%n",
                                id, lesson.getID(), lesson.getTitle(), lesson.getDuration());
                        id++;
                    }

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
                    String title = scanner.nextLine();
                    System.out.println("Lesson duration : ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Lesson Type (Theory/Coding) : ");
                    String lessonType = scanner.nextLine();
                    System.out.println("Choose course and section to add a lesson");
                    int courseID = getCourseID("Choose course(No.) to add a lesson : ");
                    scanner.nextLine();
                    int sectionID = getSectionID(courseID, "Choose section(No.) to add a lesson : ");
                    scanner.nextLine();
                    manager.addNewLesson(courseID, sectionID, lessonID, title, duration, lessonType);
                }
                case 19 -> {
                    System.out.println("Lesson Operation : Remove lesson");
                    System.out.println("Choose course and section to remove a lesson");
                    int courseID = getCourseID("Choose course(No.) to remove a lesson : ");
                    scanner.nextLine();
                    int sectionID = getSectionID(courseID, "Choose section(No.) to remove a lesson : ");
                    System.out.println("Available lessons in this section : " + manager.getListOfSections(courseID).get(sectionID - 1).getTitle());
                    manager.getListOfLessonsFromSection(courseID, sectionID).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins)%n",
                            l.getID(), l.getTitle(), l.getDuration()));
                    scanner.nextLine();
                    System.out.println("Lesson ID : ");
                    int lessonID = scanner.nextInt();
                    var isRemoved = manager.removeLesson(courseID, sectionID, lessonID);
                    System.out.println(isRemoved ? "Lesson successfully removed!" : "Cannot remove lesson!");
                    manager.getListOfLessonsFromSection(courseID, sectionID).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getID(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 20 -> {
                    System.out.println("Lesson Operation : Edit lesson name");
                    System.out.println("Choose course and section to rename a lesson");
                    int courseID = getCourseID("Choose course(No.) to rename a lesson : ");
                    scanner.nextLine();
                    int sectionID = getSectionID(courseID, "Choose section(No.) to rename a lesson : ");
                    System.out.println("Available lessons in this section : " + manager.getListOfSections(courseID).get(sectionID - 1).getTitle());
                    manager.getListOfLessonsFromSection(courseID, sectionID).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getID(), l.getTitle(), l.getDuration(), l.getType()));
                    scanner.nextLine();
                    System.out.println("Which lesson do you want change the name : ");
                    int lessonID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("New name : ");
                    String lessonName = scanner.nextLine();
                    manager.editLessonName(courseID, sectionID, lessonID, lessonName);
                    System.out.println("Lesson name changed successfully!");
                    manager.getListOfLessonsFromSection(courseID, sectionID).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getID(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 21 -> {
                    System.out.println("Lesson Operation : List lessons from particular section");
                    System.out.println("Choose course and section to list lessons");
                    int courseID = getCourseID("Choose course(No.) to list lesson : ");
                    int sectionID = getSectionID(courseID, "Choose section(No.) to list lesson : ");
                    System.out.println("Available lessons in this section : " + manager.getListOfSections(courseID).get(sectionID - 1).getTitle());
                    manager.getListOfLessonsFromSection(courseID, sectionID).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getID(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 22 -> {
                    System.out.println("Lesson Operation : List all lessons from course");
                    System.out.println("Choose course and section to list lessons");
                    int courseID = getCourseID("Choose course(No.) to list lesson : ");
                    manager.getListOfAllLessons(courseID).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getID(), l.getTitle(), l.getDuration(), l.getType()));

                }
                case 23 -> {
                    System.out.println("Lesson Operation : Lessons with same keyword");
                    System.out.println("Choose course to search for lesson with same keyword");
                    int courseID = getCourseID("Choose course(No.) : ");
                    scanner.nextLine();
                    System.out.println("Enter a keyword to search : ");
                    String keyword = scanner.nextLine();
                    var lessonsFound = manager.getLessonsWithSameKeyword(courseID, keyword);
                    if (lessonsFound == null) {
                        System.out.println("No lessons found!");
                    } else {
                        lessonsFound.forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                                l.getID(), l.getTitle(), l.getDuration(), l.getType()));
                    }
                }
                case 24 -> {
                    System.out.println("Lesson Operation : Total number of lessons");
                    int courseID = getCourseID("Choose course(No.) to count lessons : ");
                    System.out.println("Total number of lessons in this course : " + manager.getLessonsCount(courseID));
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
                21) List lessons from particular section
                22) List all lessons from course
                23) Lessons with same keyword
                24) Total number of lessons
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
        System.out.println(message);
        int courseID = scanner.nextInt();
        return courseID;
    }

    private static int getSectionID(int CourseID, String message) {
        int id = 1;
        for (Section section : manager.getListOfSections(CourseID)) {
            System.out.printf("\tSection %d %s%n", id, section.getTitle());
            id++;
        }
        System.out.println(message);
        int sectionID = scanner.nextInt();
        return sectionID;
    }
}