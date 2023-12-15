import entities.Course;
import entities.Lesson;
import entities.LessonType;
import entities.Section;
import services.CourseManager;
import services.CourseManagerImplementation;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final CourseManager courseManager = new CourseManagerImplementation();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // load sample data
        loadSampleCourses();

        boolean isApplicationRunning = true;
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
                    System.out.println("Exiting Application.....");
                    isApplicationRunning = false;
                }
                case 1 -> {
                    System.out.println("Course Operation : Creating New Course");
                    Course newCourse = null;
                    try {
                        System.out.printf("Available courses : %d%n", courseManager.getCourseCount());
                        courseManager.getCourses().forEach(c -> System.out.printf("\tCourse %d %s%n", c.getCourseId(), c.getTitle()));
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

                        newCourse = courseManager.createCourse(courseId, courseTitle, authorName, publishDate, price);
                        System.out.printf("Course Created : Course %d - %s%n", newCourse.getCourseId(), newCourse.getTitle());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Course creation FAILED! - " + e.getMessage());
                        break;
                    }

                    boolean isAddingSection = true;
                    do {
                        System.out.print("Do you want to add any section? (y/n): ");
                        String addSectionOption = scanner.nextLine();
                        switch (addSectionOption) {
                            case "y" -> {
                                Section newSection = null;
                                try {
                                    System.out.printf("Adding a section to course '%s'%n", newCourse.getTitle());
                                    System.out.printf("Available sections : %d%n", courseManager.getSectionsCount(newCourse.getCourseId()));
                                    courseManager.getSections(newCourse.getCourseId()).forEach(section ->
                                            System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
                                    System.out.print("Section Id : ");
                                    int sectionId = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Section Title : ");
                                    String sectionTitle = scanner.nextLine();

                                    newSection = courseManager.addNewSection(newCourse.getId(), sectionId, sectionTitle);
                                    System.out.printf("Section successfully added : Section %d - %s%n", newSection.getSectionId(), newSection.getTitle());
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Section creation FAILED! -  " + e.getMessage());
                                    break;
                                }
                                boolean isSectionOperationsRunning = true;
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
                                            System.out.print(courseManager.removeSection(newCourse.getId(), newSection.getId()) ?
                                                    "Section successfully removed" : "Something went wrong! Cannot remove the course!");
                                            isSectionOperationsRunning = false;
                                        }
                                        case 3 -> {
                                            Lesson newLesson = null;
                                            try {
                                                System.out.printf("Adding a lesson to 'Course - %d %s : Section %d - %s'%n", newCourse.getCourseId(),
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
                                                newLesson = courseManager.addNewLesson(newCourse.getId(), newSection.getId(), lessonId, lessonTitle, duration, lessonType);
                                                System.out.printf("Lesson successfully added : Lesson %d - %s%n", newLesson.getLessonId(), newLesson.getTitle());
                                            } catch (IllegalArgumentException e) {
                                                System.out.println("Lesson creation FAILED! - " + e.getMessage());
                                                break;
                                            }
                                            boolean isLessonOperationsRunning = true;
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
                                                    case 3 -> {
                                                        isLessonOperationsRunning = false;
                                                    }
                                                }
                                            } while (isLessonOperationsRunning);
                                        }
                                        case 4 -> isSectionOperationsRunning = false;
                                    }
                                } while (isSectionOperationsRunning);
                            }
                            case "n" -> {
                                isAddingSection = false;
                            }
                        }
                    } while (isAddingSection);

                    System.out.printf("Course successfully created : Course %d - %s%n", newCourse.getCourseId(), newCourse.getTitle());
                    courseManager.retrieveCourse(newCourse);
                    System.out.print("Do you want to write this course to file?(y/n): ");
                    String writeOption = scanner.nextLine();
                    if (writeOption.toLowerCase().charAt(0) == 'y') {
                        try {
                            courseManager.writeCourseToFile(newCourse);
                        } catch (FileAlreadyExistsException e) {
                            courseManager.handleFileAlreadyExists(newCourse);
                        }
                        System.out.println("Course successfully written to file");
                    }

                }
                case 2 -> {
                    System.out.println("Course Operation : Remove course");
                    courseManager.getCourses().forEach(c -> System.out.printf("\tCourse %d %s%n", c.getCourseId(), c.getTitle()));
                    System.out.print("\nChoose course to remove from list : ");
                    int courseId = scanner.nextInt();
                    System.out.println(courseManager.removeCourse(courseId) ? "Course successfully removed" : "Something went wrong! Please try again");
                }
                case 3 -> {
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
                case 4 -> {
                    System.out.println("Course Operation : Course List");
                    courseManager.getCourses().forEach(c -> System.out.printf("\tCourse %d - %s%n",
                            c.getCourseId(), c.getTitle()));
                }
                case 5 -> {
                    System.out.println("Course Operation : Retrieve courses");
                    System.out.println(courseManager.retrieveCourse(courseManager.getCourse(getCourseIdFromCourseList("Select a Course(No.) : "))));
                }
                case 6 -> {
                    System.out.println("Course Operation : Total number of courses");
                    System.out.printf("Total number of Courses : %d%n", courseManager.getCourseCount());
                }
                case 7 -> {
                    System.out.println("Course Operation : Write course to file");
                    courseManager.getCourses().forEach(c -> System.out.printf("\tCourse %d - %s%n",
                            c.getCourseId(), c.getTitle()));
                    System.out.print("Choose a course to write : ");
                    int courseId = scanner.nextInt();
                    var course = courseManager.getCourse(courseId);
                    System.out.println(courseManager.retrieveCourse(course));
                    System.out.println("\nWriting this course to file.....");
                    try {
                        courseManager.writeCourseToFile(course);
                        System.out.println("Course successfully written to file!");
                    } catch (FileAlreadyExistsException e) {
                        courseManager.handleFileAlreadyExists(course);
                    }
                }
                case 8 -> {
                    System.out.println("Course Operation : Read course from file");
                    int id = 1;
                    for (Path filePath : courseFilePaths) {
                        System.out.printf("\t%d %s%n", id, filePath.getFileName());
                        id++;
                    }
                    scanner.nextLine();
                    System.out.print("Which course do you want to read a file?(all/No./FileName) : ");
                    String readOption = scanner.nextLine();
                    courseManager.readCourseFromFile(readOption);

                }
                case 9 -> {
                    System.out.println("Section Operation : Add new section to course");
                    int courseId = getCourseIdFromCourseList("Choose course from list to add a section : ");
                    System.out.printf("Available sections : %d%n", courseManager.getSectionsCount(courseId));
                    courseManager.getSections(courseId).forEach(section ->
                            System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
                    try {
                        System.out.print("Section ID : ");
                        int sectionId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Section Title : ");
                        String sectionTitle = scanner.nextLine();
                        var section = courseManager.addNewSection(courseId, sectionId, sectionTitle);
                        System.out.printf("'Section %d - %s' successfully added %n", section.getSectionId(), section.getTitle());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Section creation FAILED! - " + e.getMessage());
                    }

                }
                case 10 -> {
                    System.out.println("Section Operation : Remove section from course");
                    int courseId = getCourseIdFromCourseList("From which course do you want to remove a section? Course(No.) : ");
                    System.out.println("Sections available in " + courseManager.getCourse(courseId).getTitle());
                    int sectionId = getSectionIdFromSectionList(courseId, "Select section(No.) to remove : ");
                    courseManager.removeSection(courseId, sectionId);
                    System.out.println("Section successfully removed from course. \nAvailable sections");

                    courseManager.getSections(courseId).forEach(section ->
                            System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
                }
                case 11 -> {
                    System.out.println("Section Operation : Edit section name");
                    int courseId = getCourseIdFromCourseList("From which course do you want to rename section? Course(No.) : ");
                    System.out.println("Sections available in " + courseManager.getCourse(courseId).getTitle());
                    int sectionId = getSectionIdFromSectionList(courseId, "Select section(No.) to rename : ");
                    scanner.nextLine();
                    System.out.print("Enter new section name : ");
                    String title = scanner.nextLine();
                    courseManager.editSectionName(courseId, sectionId, title);
                    System.out.println("Course name successfully changed to " + title);

                }
                case 12 -> {
                    System.out.println("Section Operation : Sections List");
                    int courseId = getCourseIdFromCourseList("List sections from course(No.) : ");
                    courseManager.getSections(courseId).forEach(section ->
                            System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
                }
                case 13 -> {
                    System.out.println("Section Operation : Total number of sections in a course");
                    int courseId = getCourseIdFromCourseList("From which course do you want to count sections? Course(No.) : ");
                    System.out.printf("Total number of sections in %s is : %d%n",
                            courseManager.getCourses().get(courseId - 1).getTitle(), courseManager.getSectionsCount(courseId));
                }
                case 14 -> {
                    System.out.println("Section Operation : Shortest section based on lesson duration");
                    System.out.println("Choose course to filter shortest section");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) : ");
                    var section = courseManager.getShortestSection(courseId);
                    System.out.println("Shortest section in Course : " + courseManager.getCourses().get(courseId).getTitle());
                    System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle());
                }
                case 15 -> {
                    System.out.println("Section Operation : Longest section based on lesson duration");
                    System.out.println("Choose course to filter Longest section");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) : ");
                    var section = courseManager.getLongestSection(courseId);
                    System.out.println("Longest section in Course : " + courseManager.getCourses().get(courseId).getTitle());
                    System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle());
                }
                case 16 -> {
                    System.out.println("Section Operation : Longest lesson in each section based on lesson duration");
                    System.out.println("Choose course to list longest lesson in each section");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) to list lesson : ");
                    scanner.nextLine();
                    System.out.println("Longest lessons in each section of this course");
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
                    var section = courseManager.getSectionWithMostLessons(getCourseIdFromCourseList("From which course do you want to know section with MOST lessons ? Course(No.) : "));
                    System.out.printf("'Section %d - %s' is the section with most lessons of count : %d%n",
                            section.getSectionId(), section.getTitle(), section.getLessons().size());
                }
                case 18 -> {
                    System.out.println("Lesson Operation : Section with most coding lessons");
                    int courseId = getCourseIdFromCourseList("From which course do you want to know section with MOST CODING lessons ? Course(No.) : ");
                    var section = courseManager.getSectionWithMostCodingLessons(courseId);
                    List<Lesson> codingLessons = courseManager.getSection(courseId, section.getSectionId()).getLessons().stream()
                            .filter(lesson -> lesson.getType().equals(LessonType.CODING)).toList();
                    System.out.printf("'Section %d - %s' is the section with MOST CODING lessons of count : %d%n",
                            section.getSectionId(), section.getTitle(), codingLessons.size());
                }
                case 19 -> {
                    System.out.println("Lesson Operation : Section with most theory lessons");
                    int courseId = getCourseIdFromCourseList("From which course do you want to know section with MOST THEORY lessons ? Course(No.) : ");
                    var section = courseManager.getSectionWithMostTheoryLessons(courseId);
                    List<Lesson> theoryLessons = courseManager.getSection(courseId, section.getSectionId()).getLessons().stream()
                            .filter(lesson -> lesson.getType().equals(LessonType.THEORY)).toList();
                    System.out.printf("'Section %d - %s' is the section with MOST CODING lessons of count : %d%n",
                            section.getSectionId(), section.getTitle(), theoryLessons.size());
                }
                case 20 -> {
                    System.out.println("Lesson Operation : Add new lesson");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) to add a lesson : ");
                    scanner.nextLine();
                    int sectionId = getSectionIdFromSectionList(courseId, "Choose section(No.) to add a lesson : ");
                    scanner.nextLine();
                    System.out.printf("%dAvailable lessons in '%s' %n",
                            courseManager.getSection(courseId, sectionId).getLessons().size(), courseManager.getSection(courseId, sectionId).getTitle());
                    courseManager.getLessons(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration()));
                    try {
                        System.out.print("Lesson Type (Theory/Coding) : ");
                        String lessonType = scanner.nextLine();
                        System.out.print("Lesson Id : ");
                        int lessonID = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Lesson Title : ");
                        String title = scanner.nextLine();
                        System.out.print("Lesson duration : ");
                        int duration = scanner.nextInt();
                        courseManager.addNewLesson(courseId, sectionId, lessonID, title, duration, lessonType);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Lesson creation FAILED! - " + e.getMessage());
                    }
                }
                case 21 -> {
                    System.out.println("Lesson Operation : Remove lesson");
                    System.out.println("Choose course and section to remove a lesson");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) to remove a lesson : ");
                    scanner.nextLine();
                    int sectionId = getSectionIdFromSectionList(courseId, "Choose section(No.) to remove a lesson : ");
                    System.out.println("Available lessons in this section : " + courseManager.getSections(courseId).get(sectionId - 1).getTitle());
                    courseManager.getLessons(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                    scanner.nextLine();
                    System.out.print("Lesson Id : ");
                    int lessonId = scanner.nextInt();
                    System.out.println(courseManager.removeLesson(courseId, sectionId, lessonId) ?
                            "Lesson successfully Removed" : "Cannot delete the lesson!");
                    courseManager.getLessons(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 22 -> {
                    System.out.println("Lesson Operation : Edit lesson name");
                    System.out.println("Choose course and section to rename a lesson");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) to rename a lesson : ");
                    scanner.nextLine();
                    int sectionId = getSectionIdFromSectionList(courseId, "Choose section(No.) to rename a lesson : ");
                    System.out.println("Available lessons in this section : " + courseManager.getSections(courseId).get(sectionId - 1).getTitle());
                    courseManager.getLessons(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                    scanner.nextLine();
                    System.out.print("Which lesson do you want change the name : ");
                    int lessonID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New name : ");
                    String lessonName = scanner.nextLine();
                    courseManager.editLessonName(courseId, sectionId, lessonID, lessonName);
                    System.out.println("Lesson name changed successfully!");
                    courseManager.getLessons(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 23 -> {
                    System.out.println("Lesson Operation : List lessons from particular section");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) : ");
                    int sectionId = getSectionIdFromSectionList(courseId, "Choose section(No.) to list all lessons : ");
                    System.out.println("Available lessons in this section : " + courseManager.getSections(courseId).get(sectionId - 1).getTitle());
                    courseManager.getLessons(courseId, sectionId).forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                            l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                }
                case 24 -> {
                    System.out.println("Lesson Operation : Lessons with same keyword");
                    System.out.println("Choose course to search for lesson with same keyword");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) : ");
                    scanner.nextLine();
                    System.out.print("Enter a keyword to search : ");
                    String keyword = scanner.nextLine();
                    var lessonsFound = courseManager.getLessonsWithSameKeyword(courseId, keyword);
                    if (lessonsFound == null) {
                        System.out.println("No lessons found!");
                    } else {
                        lessonsFound.forEach(l -> System.out.printf("\tLesson %d - %s (%d mins) (%s)%n",
                                l.getLessonId(), l.getTitle(), l.getDuration(), l.getType()));
                    }
                }
                case 25 -> {
                    System.out.println("Lesson Operation : Total number of lessons");
                    int courseId = getCourseIdFromCourseList("Choose course(No.) to count lessons : ");
                    System.out.println("Total number of lessons in this course : " + courseManager.getLessonsCount(courseId));
                }

                default -> System.out.println("INVALID_VALUE, Choose correct option!!!");
            }

        } while (isApplicationRunning);

    }

    private static void printMenu() {
        String CourseManagerMenuDescription = """
                                
                COURSE OPERATIONS
                1) Add course
                2) Remove course
                3) Edit Course name
                4) List courses
                5) Retrieve course
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
                18) Section with most coding lessons
                19) Section with most theory lessons
                                
                LESSON OPERATIONS
                20) Add lesson
                21) Remove lesson
                22) Edit lesson name
                23) List lessons
                24) Lessons with same keyword
                25) Total number of lessons
                Press 0 to exit()
                                
                """;
        System.out.println(CourseManagerMenuDescription);
    }

    private static void loadSampleCourses() {
        courseManager.loadSampleDataOne();
        courseManager.loadSampleDataTwo();
    }

    private static int getCourseIdFromCourseList(String message) {
        courseManager.getCourses().forEach(course ->
                System.out.printf("\tCourse %d %s%n", course.getCourseId(), course.getTitle()));
        System.out.print(message);
        return scanner.nextInt();
    }

    private static int getSectionIdFromSectionList(int courseId, String message) {
        courseManager.getSections(courseId).forEach(section ->
                System.out.printf("\tSection %d - %s%n", section.getSectionId(), section.getTitle()));
        System.out.print(message);
        return scanner.nextInt();
    }
}