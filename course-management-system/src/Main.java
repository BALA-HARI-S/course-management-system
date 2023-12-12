import entities.Lesson;
import services.CourseManagerImplementation;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CourseManagerImplementation courseManager = new CourseManagerImplementation();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // load sample data
        courseManager.loadSampleDataOne();
        courseManager.loadSampleDataTwo();

        boolean flag = true;
        do {
            printMenu();
            System.out.print("How can I help you (Choose any option): ");

            Path rootDirectory = Path.of(".");
            String subdirectoryName = "courses";
            Path courseDirectoryPath = rootDirectory.resolve(subdirectoryName);
            final List<Path> courseFilePaths = courseManager.getListOfCourseFiles(courseDirectoryPath);

            int option = scanner.nextInt();
            switch (option) {
                case 0 -> {
                    System.out.println("Exit Application");
                    flag = false;
                }
                case 1 -> {
                    System.out.print("Course Operation : Creating New Course\n");
                    System.out.print("Course ID : ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Course Title : ");
                    String courseTitle = scanner.nextLine();

                    System.out.print("Course Author's Name : ");
                    String authorName = scanner.nextLine();

                    System.out.print("Course Publish date (dd-MM-yyyy): ");
                    String publishDate = scanner.nextLine();

                    System.out.print("Course Price(₹) : ");
                    double price = scanner.nextDouble();

                    System.out.print("Course Rating : ");
                    double rating = scanner.nextDouble();
                    scanner.nextLine();

                    var newCourse = courseManager.createCourse(courseId, courseTitle, authorName, publishDate, price, rating);
                    System.out.printf("Course Created : %d Course %d - %s%n", newCourse.getId(), newCourse.getCourseId(), newCourse.getTitle());

                    boolean sectionFlag = true;
                    do {
                        System.out.print("Do you want to add sections? (y/n): ");
                        String addSectionOption = scanner.nextLine();
                        switch (addSectionOption) {
                            case "y" -> {
                                System.out.printf("Adding a section to course '%s'%n", newCourse.getTitle());
                                System.out.print("Section Id : ");
                                int sectionId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Section Title : ");
                                String sectionTitle = scanner.nextLine();

                                var newSection = courseManager.addNewSection(newCourse.getId(), sectionId, sectionTitle);
                                System.out.printf("Section successfully added : Section %d - %s%n", newSection.getSectionId(), newSection.getTitle());

                                boolean sectionOperationFlag = true;
                                do {
                                    System.out.print("""
                                            1) Edit this section's name
                                            2) Remove this section
                                            3) Add Lessons
                                            4) Skip
                                                                                        
                                            """);
                                    System.out.print("Option : ");
                                    int sectionOperationOption = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (sectionOperationOption) {
                                        case 1 -> {
                                            System.out.print("\nEnter new section title : ");
                                            String newSectionTitle = scanner.nextLine();
                                            courseManager.editSectionName(newCourse.getId(), newSection.getId(), newSectionTitle);
                                            System.out.printf("Section name successfully changed to '%s'%n", newSection.getTitle());
                                        }
                                        case 2 -> {
                                            courseManager.removeSection(newCourse.getId(), newSection.getId());
                                            System.out.print("Section successfully removed");
                                        }
                                        case 3 -> {
                                            boolean lessonFlag = true;
                                            do {
                                                System.out.print("Do you want to add a lesson? (y/n): ");
                                                String addLessonOption = scanner.nextLine();
                                                switch (addLessonOption) {
                                                    case "y" -> {
                                                        System.out.printf("Adding a lesson to ''Course - %d %s : Section %d - %s''%n", newCourse.getCourseId(),
                                                                newCourse.getTitle(), newSection.getSectionId(), newSection.getTitle());
                                                        System.out.print("Lesson Id : ");
                                                        int lessonId = scanner.nextInt();
                                                        scanner.nextLine();
                                                        System.out.print("Lesson Title : ");
                                                        String lessonTitle = scanner.nextLine();
                                                        System.out.print("Lesson duration (Minutes) : ");
                                                        int duration = scanner.nextInt();
                                                        scanner.nextLine();
                                                        System.out.print("Lesson Type (Theory/Coding) : ");
                                                        String lessonType = scanner.nextLine();
                                                        var newLesson = courseManager.addNewLesson(newCourse.getId(), newSection.getId(), lessonId, lessonTitle, duration, lessonType);
                                                        System.out.printf("Lesson successfully added : Lesson %d - %s%n", newLesson.getLessonId(), newLesson.getTitle());

                                                        boolean lessonOperationFlag = true;
                                                        do {
                                                            System.out.print("""
                                                                    1) Edit this lesson's name
                                                                    2) Remove this lesson
                                                                    3) Skip
                                                                                                                
                                                                    """);
                                                            System.out.print("Option : ");
                                                            int lessonOperationOption = scanner.nextInt();
                                                            scanner.nextLine();
                                                            switch (lessonOperationOption) {
                                                                case 1 -> {
                                                                    System.out.print("Enter new lesson title : ");
                                                                    String newLessonTitle = scanner.nextLine();
                                                                    courseManager.editLessonName(newCourse.getId(), newSection.getId(), newLesson.getId(), newLessonTitle);
                                                                    System.out.printf("Lesson name successfully changed to '%s'%n", newSection.getTitle());
                                                                }
                                                                case 2 -> {
                                                                    courseManager.removeLesson(newCourse.getId(), newSection.getId(), newLesson.getId());
                                                                    System.out.print("Lesson successfully removed\n");
                                                                }
                                                                case 3 -> lessonOperationFlag = false;
                                                            }
                                                        } while (lessonOperationFlag);
                                                    }
                                                    case "n" -> lessonFlag = false;
                                                }
                                            } while (lessonFlag);
                                        }
                                        case 4 -> sectionOperationFlag = false;
                                    }
                                } while (sectionOperationFlag);
                            }
                            case "n" -> sectionFlag = false;
                        }
                    } while (sectionFlag);

                    System.out.printf("Course successfully created : Course %d - %s%n", newCourse.getCourseId(), newCourse.getTitle());
                    courseManager.printCourse(newCourse);
                    System.out.print("Do you want to write this course to file?(y/n): ");
                    String writeOption = scanner.nextLine();
                    if (writeOption.toLowerCase().charAt(0) == 'y') {
                        courseManager.writeCourseToFile(newCourse);
                        System.out.println("Course successfully written to file");
                    }

                }
                case 2 -> {
                    System.out.println("Course Operation : Remove course from list");
                    courseManager.getCourses().forEach(c -> System.out.printf("\tCourse %d %s%n", c.getCourseId(), c.getTitle()));
                    System.out.print("\nChoose course to remove from list : ");
                    int i = scanner.nextInt();
                    courseManager.removeCourseFromList(i);
                }
                case 3 -> {
                    System.out.println("Course Operation : Remove course file");
                    int id = 1;
                    for (Path filePath : courseFilePaths) {
                        System.out.printf("\tCourse %d %s%n", id, filePath.getFileName());
                        id++;
                    }
                    System.out.print("\nWhich course file do you want delete?(No.) : ");
                    int i = scanner.nextInt();
                    if (i > 0 && i < courseManager.getListOfCourseFiles(courseDirectoryPath).size()) {
                        boolean removeCourse = courseManager.removeCourseFile(courseFilePaths.get(i - 1));
                        System.out.println(removeCourse ?
                                "Course file successfully removed!" : "There was a problem occurred when removing course file!");
                        return;
                    }
                    System.out.println("Invalid file number, Please select correct file number from the list.");

                }
                case 4 -> {
                    System.out.println("Course Operation : Edit course name");
                    courseManager.getCourses().forEach(c -> System.out.printf("\tCourse %d %s%n",
                            c.getCourseId(), c.getTitle()));
                    System.out.print("Choose course from list to change it's name : ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new course name : ");
                    String title = scanner.nextLine();
                    courseManager.editCourseName(courseId, title);
                    System.out.println("Course name successfully changed to " + title);
                }
                case 5 -> {
                    System.out.println("Course Operation : Course List");
                    courseManager.getCourses().forEach(c -> System.out.printf("\t%d) Course %d - %s%n",
                            c.getId(), c.getCourseId(), c.getTitle()));
                }
                case 6 -> {
                    System.out.println("Course Operation : Total number of courses - both list and files");
                    System.out.printf("Number of Courses in List : %d%nNumber of Course Files: %d%n",
                            courseManager.getCourseCount(), courseManager.getCourseFilesCount());
                }
                case 7 -> {
                    System.out.println("Course Operation : Write course to file");
                    courseManager.getCourses().forEach(c -> System.out.printf("\t%d) Course %d - %s%n",
                            c.getId(), c.getCourseId(), c.getTitle()));
                    System.out.print("Choose a course to write : ");
                    int courseId = scanner.nextInt();
                    courseManager.writeCourseToFile(courseManager.getCourse(courseId));
                    System.out.print("Course successfully written to file\n");
                    courseManager.printCourse(courseManager.getCourse(courseId));
                }
                case 8 -> {
                    System.out.println("Course Operation : Read course from file");
                    int id = 1;
                    for (Path filePath : courseFilePaths) {
                        System.out.printf("\tCourse %d %s%n", id, filePath.getFileName());
                        id++;
                    }
                    scanner.nextLine();
                    System.out.print("Which course do you want to read a file?(all/No./FileName) : ");
                    String readOption = scanner.nextLine();
                    courseManager.readCourseFromFile(readOption);

                }
                case 9 -> {
                    System.out.println("Section Operation : Add new section to course");
                    System.out.print("Section ID : ");
                    int sectionId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Section Title : ");
                    String sectionTitle = scanner.nextLine();
                    int courseId = getCourseId("Choose course from list to add a section : ");
                    courseManager.addNewSection(courseId, sectionId, sectionTitle);
                }
                case 10 -> {
                    System.out.println("Section Operation : Remove section from course");
                    int courseId = getCourseId("From which course do you want to remove a section? Course(No.) : ");
                    System.out.println("Sections available in " + courseManager.getCourse(courseId).getTitle());
                    int sectionId = getSectionId(courseId, "Select section(No.) to remove : ");
                    courseManager.removeSection(courseId, sectionId);
                    System.out.println("Section successfully removed from course. Available sections");

                    courseManager.getSections(courseId).forEach(section ->
                            System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
                }
                case 11 -> {
                    System.out.println("Section Operation : Edit section name");
                    int courseId = getCourseId("From which course do you want to rename section? Course(No.) : ");
                    System.out.println("Sections available in " + courseManager.getCourse(courseId).getTitle());
                    int sectionId = getSectionId(courseId, "Select section(No.) to rename : ");
                    scanner.nextLine();
                    System.out.println("Enter new section name : ");
                    String title = scanner.nextLine();
                    courseManager.editSectionName(courseId, sectionId, title);
                    System.out.println("Course name successfully changed to " + title);

                }
                case 12 -> {
                    System.out.println("Section Operation : Sections List");
                    int courseId = getCourseId("List sections from course(No.) : ");
                    courseManager.getSections(courseId).forEach(section ->
                            System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
                }
                case 13 -> {
                    System.out.println("Section Operation : Total number of sections in a course");
                    int courseId = getCourseId("From which course do you want to count sections? Course(No.) : ");
                    System.out.printf("Total number of sections in %s is : %d%n",
                            courseManager.getCourses().get(courseId - 1).getTitle(), courseManager.getSectionsCount(courseId));
                }
                case 14 -> {
                    System.out.println("Section Operation : Shortest section based on lesson duration");
                    System.out.println("Choose course to filter shortest section");
                    int courseId = getCourseId("Choose course(No.) : ");
                    var section = courseManager.getShortestSection(courseId);
                    System.out.println("Shortest section in Course : " + courseManager.getCourses().get(courseId).getTitle());
                    System.out.printf("\tSection %d - %s", section.getSectionId(), section.getTitle());
                }
                case 15 -> {
                    System.out.println("Section Operation : Longest section based on lesson duration");
                    System.out.println("Choose course to filter Longest section");
                    int courseId = getCourseId("Choose course(No.) : ");
                    var section = courseManager.getLongestSection(courseId);
                    System.out.println("Shortest section in Course : " + courseManager.getCourses().get(courseId).getTitle());
                    System.out.printf("\tSection %d - %s", section.getSectionId(), section.getTitle());
                }
                case 16 -> {
                    System.out.println("Section Operation : Longest lesson in each section based on lesson duration");
                    System.out.println("Choose course to list longest lesson in each section");
                    int courseId = getCourseId("Choose course(No.) to list lesson : ");
                    System.out.println("Longest lessons in each section of this course : ");
                    var lessons = courseManager.getLongestLesson(courseId);
                    int id = 1;
                    for (Lesson lesson : lessons) {
                        System.out.printf("\tSection %d : Lesson %d - %s (%d mins)%n",
                                id, lesson.getLessonId(), lesson.getTitle(), lesson.getDuration());
                        id++;
                    }

                }
                case 17 -> {
                    System.out.println("Section operation : Sections with most lessons");
                    var section = courseManager.getSectionWithMostLessons(getCourseId("From which course do you want to know section with most lessons ? Course(No.) : "));
                    System.out.printf("'Section %d - %s' is the section with most lessons of count : %d%n",
                            section.getSectionId(), section.getTitle(), section.getLessons().size());
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
                    int courseId = getCourseId("Choose course(No.) to add a lesson : ");
                    scanner.nextLine();
                    int sectionId = getSectionId(courseId, "Choose section(No.) to add a lesson : ");
                    scanner.nextLine();
                    courseManager.addNewLesson(courseId, sectionId, lessonID, title, duration, lessonType);
                }
                case 19 -> {
                    System.out.println("Lesson Operation : Remove lesson");
                    System.out.println("Choose course and section to remove a lesson");
                    int courseId = getCourseId("Choose course(No.) to remove a lesson : ");
                    scanner.nextLine();
                    int sectionId = getSectionId(courseId, "Choose section(No.) to remove a lesson : ");
                    System.out.println("Available lessons in this section : " + courseManager.getSections(courseId).get(sectionId - 1).getTitle());
                    courseManager.getListOfLessonsFromSection(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration()));
                    scanner.nextLine();
                    System.out.println("Lesson ID : ");
                    int lessonID = scanner.nextInt();
                    var isRemoved = courseManager.removeLesson(courseId, sectionId, lessonID);
                    System.out.println(isRemoved ? "Lesson successfully removed!" : "Cannot remove lesson!");
                    courseManager.getListOfLessonsFromSection(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 20 -> {
                    System.out.println("Lesson Operation : Edit lesson name");
                    System.out.println("Choose course and section to rename a lesson");
                    int courseId = getCourseId("Choose course(No.) to rename a lesson : ");
                    scanner.nextLine();
                    int sectionId = getSectionId(courseId, "Choose section(No.) to rename a lesson : ");
                    System.out.println("Available lessons in this section : " + courseManager.getSections(courseId).get(sectionId - 1).getTitle());
                    courseManager.getListOfLessonsFromSection(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                    scanner.nextLine();
                    System.out.println("Which lesson do you want change the name : ");
                    int lessonID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("New name : ");
                    String lessonName = scanner.nextLine();
                    courseManager.editLessonName(courseId, sectionId, lessonID, lessonName);
                    System.out.println("Lesson name changed successfully!");
                    courseManager.getListOfLessonsFromSection(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 21 -> {
                    System.out.println("Lesson Operation : List lessons from particular section");
                    System.out.println("Choose course and section to list lessons");
                    int courseId = getCourseId("Choose course(No.) to list lesson : ");
                    int sectionId = getSectionId(courseId, "Choose section(No.) to list lesson : ");
                    System.out.println("Available lessons in this section : " + courseManager.getSections(courseId).get(sectionId - 1).getTitle());
                    courseManager.getListOfLessonsFromSection(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 22 -> {
                    System.out.println("Lesson Operation : List all lessons from course");
                    System.out.println("Choose course and section to list lessons");
                    int courseId = getCourseId("Choose course(No.) to list lesson : ");
                    courseManager.getListOfAllLessons(courseId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));

                }
                case 23 -> {
                    System.out.println("Lesson Operation : Lessons with same keyword");
                    System.out.println("Choose course to search for lesson with same keyword");
                    int courseId = getCourseId("Choose course(No.) : ");
                    scanner.nextLine();
                    System.out.println("Enter a keyword to search : ");
                    String keyword = scanner.nextLine();
                    var lessonsFound = courseManager.getLessonsWithSameKeyword(courseId, keyword);
                    if (lessonsFound == null) {
                        System.out.println("No lessons found!");
                    } else {
                        lessonsFound.forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                                l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                    }
                }
                case 24 -> {
                    System.out.println("Lesson Operation : Total number of lessons");
                    int courseId = getCourseId("Choose course(No.) to count lessons : ");
                    System.out.println("Total number of lessons in this course : " + courseManager.getLessonsCount(courseId));
                }

                default -> System.out.println("INVALID_VALUE, Choose correct option!!!");
            }

        } while (flag);

    }

    private static void printMenu() {
        String textBlock = """
                COURSE OPERATIONS
                1) Add course
                2) Remove course from list
                3) Remove course file
                4) Edit Course name
                5) List courses
                6) Total number of courses
                7) Write course to file
                8) Read course from file
                                
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

    private static int getCourseId(String message) {
        int id = 1;
        courseManager.getCourses().forEach(course ->
                System.out.printf("\tCourse %d %s%n", course.getCourseId(), course.getTitle()));
        System.out.print(message);
        int courseId = scanner.nextInt();
        return courseId;
    }

    private static int getSectionId(int courseId, String message) {
        courseManager.getSections(courseId).forEach(section ->
                System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
        System.out.print(message);
        int sectionId = scanner.nextInt();
        return sectionId;
    }
}