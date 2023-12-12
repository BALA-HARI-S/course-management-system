package services;

import entities.*;
import utilities.CourseFileHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CourseManagerImplementation implements CourseManager {
    // datastore for handling courses
    private final List<Course> courses = new ArrayList<>();
    private final CourseFileHandler fileHandler = new CourseFileHandler();

    // Load sample data for Course One
    public void loadSampleDataOne() {
        Course jvm = createCourse(1, "Java Programming Masterclass for Software Developers", "Tim", "12-12-2023",
                200.00, 4.5);
        Lesson lesson1 = new TheoryLesson(1, "Introduction to the course", 2);
        Lesson lesson2 = new CodingLesson(2, "Remaster in progress", 10);
        Section section2 = new Section(2, "IntelliJ Setup");
        Lesson lesson3 = new TheoryLesson(3, "IntelliJ Installation", 2);
        Lesson lesson4 = new CodingLesson(4, "Remaster in progress", 3);
        Section section1 = new Section(1, "Course Introduction");
        jvm.addSection(section1);
        section1.addLesson(lesson1);
        section1.addLesson(lesson2);
        jvm.addSection(section2);
        section2.addLesson(lesson3);
        section2.addLesson(lesson4);
    }

    // Load sample data for Course Two
    public void loadSampleDataTwo() {
        var webDevelopment = createCourse(2, "Web Development for front-end developers", "Tim", "12-12-2023",
                200.00, 4.5);
        var section1 = addNewSection(webDevelopment.getId(), 1, "Course Introduction");
        addNewLesson(webDevelopment.getId(), section1.getId(), 1, "Introduction to the course", 2, "theory");
        addNewLesson(webDevelopment.getId(), section1.getId(), 2, "Remaster in progress", 10, "coding");
        addNewLesson(webDevelopment.getId(), section1.getId(), 3, "Introduction to the HTML,CSS,JS", 2, "theory");
        addNewLesson(webDevelopment.getId(), section1.getId(), 4, "Remaster in progress", 10, "coding");
        var section2 = addNewSection(webDevelopment.getId(), 2, "VS code Setup");
        addNewLesson(webDevelopment.getId(), section2.getId(), 5, "VS code Installation", 30, "theory");
        addNewLesson(webDevelopment.getId(), section2.getId(), 6, "Remaster in progress", 30, "coding");
    }

    //COURSE OPERATIONS

    // Create a new course
    public Course createCourse(int courseId, String title, String authorName,
                               String datePublished, double cost, double rating) {
        Course course = new Course(courseId, title, authorName,
                datePublished, cost, rating);
        getCourses().add(course);
        return course;
    }

    @Override
    public Course getCourse(int courseId) {
        return getCourses().stream()
                .filter(course -> course.getId() == courseId)
                .findFirst()
                .orElse(null);
    }

    public void removeCourseFromList(int courseId) {
        getCourses().remove(getCourse(courseId));
    }

    public boolean removeCourseFile(Path path) {
        return fileHandler.removeCourseFile(path);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Path> getListOfCourseFiles(Path path) {
        return fileHandler.listCourseFiles(path);
    }

    public int getCourseCount() {
        return getCourses().size();
    }

    public int getCourseFilesCount() {
        return getListOfCourseFiles(Paths.get(".")).size();
    }

    public void editCourseName(int courseId, String title) {
        var course = getCourse(courseId);
        if (course != null) {
            course.setTitle(title);
            return;
        }
        System.out.println("Course not found for the provided course ID.");
    }

    public void printCourse(Course course) {
        System.out.printf("Course %d - %s", course.getId(), course.getTitle());
        for (Section section : course.getSections()) {
            System.out.printf("%n\tSection %d - %s", section.getId(), section.getTitle());
            for (Lesson lesson : section.getLessons()) {
                System.out.printf("%n\t\tLesson %d - %s (%d mins) (%s)%n", lesson.getId(), lesson.getTitle(), lesson.getDuration(), lesson.getType());
            }
        }
    }

    // Write a course to a file
    public void writeCourseToFile(Course course) {
        String filename = course.getTitle() + ".txt";
        Path rootDirectory = Paths.get(".");
        String subdirectory = "courses";
        Path directoryPath = rootDirectory.resolve(subdirectory);
        Path filePath = directoryPath.resolve(filename);

        if (Files.exists(filePath)) {
            handleFileAlreadyExists(course);
        } else {
            fileHandler.writeCourse(course, course.getTitle(), ".txt");
        }

    }

    private void handleFileAlreadyExists(Course course) {
        Scanner scanner = new Scanner(System.in);
        String filename = course.getTitle();

        System.out.printf("File already exists: %s%n\t1)Override this file%n\t2)Create new copy of this file%n%nOption : ", filename);
        int userOption = scanner.nextInt();

        if (userOption == 1) {
            fileHandler.writeCourse(course, course.getTitle(), ".txt");
        } else if (userOption == 2) {
            System.out.println("Give new name for the file: ");
            scanner.nextLine();
            String newTitle = scanner.nextLine();
            fileHandler.writeCourse(course, newTitle, ".txt");
        } else {
            System.out.println("Invalid option. No action taken.");
        }
    }

    // Read a course from file
    public void readCourseFromFile(String fileReadOption) {
        fileHandler.readCourse(fileReadOption);
    }

    // SECTION OPERATIONS

    public Section addNewSection(int courseId, int sectionId, String title) {
        var section = new Section(sectionId, title);
        getCourse(courseId).addSection(section);
        return section;
    }

    public boolean removeSection(int courseId, int sectionId) {
        var sections = getCourse(courseId).getSections();
        return sections.remove(getSection(courseId, sectionId));
    }

    @Override
    public Section getSection(int courseId, int sectionId) {
        return getCourse(courseId).getSections().stream()
                .filter(section -> section.getId() == sectionId)
                .findFirst()
                .orElse(null);
    }

    public void editSectionName(int courseId, int sectionId, String title) {
        var section = getSection(courseId, sectionId);
        if (section != null) {
            section.setTitle(title);
            return;
        }
        System.out.println("Section not found for the provided section ID.");
    }

    public List<Section> getSections(int courseId) {
        return getCourse(courseId).getSections();
    }

    public int getSectionsCount(int courseId) {
        return getCourse(courseId).getSections().size();
    }

    public Section getShortestSection(int courseId) {
        var sections = getSections(courseId);
        sections.sort(new SortestSectionComparator());
        return sections.get(0);
    }

    public Section getLongestSection(int courseId) {
        var sections = getSections(courseId);
        sections.sort(new SortestSectionComparator().reversed());
        return sections.get(0);
    }

    public List<Lesson> getLongestLesson(int courseId) {
        var sections = getSections(courseId);
        List<Lesson> longestLessons = new ArrayList<>();
        for (Section section : sections) {
            var lessons = section.getLessons();
            lessons.sort(new LogestLessonComparator().reversed());
            longestLessons.add(lessons.get(0));
        }
        return longestLessons;
    }

    public Section getSectionWithMostLessons(int courseId) {
        var sections = getSections(courseId);
        sections.sort(new SectionLessonComparator().reversed());
        return sections.get(0);
    }

    // LESSON OPERATIONS
    public Lesson addNewLesson(int courseId, int sectionId,
                               int lessonId, String title, int duration, String type) {
        if (type.toLowerCase().charAt(0) == 't') {
            Lesson lesson = new TheoryLesson(lessonId, title, duration);
            getSection(courseId, sectionId).addLesson(lesson);
            return lesson;
        }
        Lesson lesson = new CodingLesson(lessonId, title, duration);
        getSection(courseId, sectionId).addLesson(lesson);
        return lesson;
    }

    public boolean removeLesson(int courseId, int sectionId,
                                int lessonId) {
        var lessons = getSection(courseId, sectionId).getLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getId() == lessonId) {
                if (lessons.remove(lesson)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Lesson getLesson(int courseId, int sectionId, int lessonId) {
        return getSection(courseId, sectionId).getLessons().stream()
                .filter(lesson -> lesson.getId() == lessonId)
                .findFirst()
                .orElse(null);
    }

    public void editLessonName(int courseId, int sectionId,
                               int lessonId, String title) {
        getLesson(courseId, sectionId, lessonId).setTitle(title);
    }

    public List<Lesson> getListOfAllLessons(int courseId) {
        var sections = getSections(courseId);
        List<Lesson> lessonsList = new ArrayList<>();
        for (Section section : sections) {
            lessonsList.addAll(section.getLessons());
        }
        return lessonsList;
    }

    public List<Lesson> getListOfLessonsFromSection(int courseId, int sectionId) {
        return getSection(courseId, sectionId).getLessons();
    }

    public List<Lesson> getLessonsWithSameKeyword(int courseId, String keyword) {
        var lessonsList = getListOfAllLessons(courseId);
        List<Lesson> lessonsFound = new ArrayList<>();
        for (Lesson lesson : lessonsList) {
            String[] words = lesson.getTitle().split(" ");
            for (String word : words) {
                if (word.equalsIgnoreCase(keyword)) {
                    lessonsFound.add(lesson);
                }
            }
        }
        return lessonsFound;
    }

    public int getLessonsCount(int courseId) {
        var sections = getCourse(courseId).getSections();
        int lessonsCount = 0;
        for (Section section : sections) {
            lessonsCount = section.getLessons().size();
        }
        return lessonsCount;
    }

}

class SectionLessonComparator implements Comparator<Section> {
    @Override
    public int compare(Section section1, Section section2) {
        return Integer.compare(section1.getLessons().size(), section2.getLessons().size());
    }
}

class SortestSectionComparator implements Comparator<Section> {
    @Override
    public int compare(Section section1, Section section2) {
        int sectionOneDuration = 0;
        int sectionTwoDuration = 0;

        for (Lesson lesson : section1.getLessons()) {
            sectionOneDuration += lesson.getDuration();
        }

        for (Lesson lesson : section2.getLessons()) {
            sectionTwoDuration += lesson.getDuration();
        }

        return Integer.compare(sectionOneDuration, sectionTwoDuration);
    }
}

class LogestLessonComparator implements Comparator<Lesson> {
    @Override
    public int compare(Lesson lesson1, Lesson lesson2) {
        return Integer.compare(lesson1.getDuration(), lesson2.getDuration());
    }
}


