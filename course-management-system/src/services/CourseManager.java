package services;

import entities.*;
import utilities.CourseFileHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseManager implements CourseManagerInterface {
    private final List<Course> courses = new ArrayList<>();
    private final CourseFileHandler fileHandler = new CourseFileHandler();

    public void loadSampleDataOne() {
        Course jvm = createNewCourse(1, "Java Programming Masterclass for Software Developers", "Tim", "29/12/23",
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
        writeCourseToFile(jvm);
    }

    public void loadSampleDataTwo() {
        var webDevelopment = createNewCourse(2, "Web Development for front-end developers", "Tim", "29/12/23",
                200.00, 4.5);
        var section1 = addNewSection(webDevelopment.getID(), 1, "Course Introduction");
        addNewLesson(webDevelopment.getID(), section1.getID(), 1, "Introduction to the course", 2, "theory");
        addNewLesson(webDevelopment.getID(), section1.getID(), 2, "Remaster in progress", 10, "coding");
        addNewLesson(webDevelopment.getID(), section1.getID(), 3, "Introduction to the HTML,CSS,JS", 2, "theory");
        addNewLesson(webDevelopment.getID(), section1.getID(), 4, "Remaster in progress", 10, "coding");
        var section2 = addNewSection(webDevelopment.getID(), 2, "VS code Setup");
        addNewLesson(webDevelopment.getID(), section2.getID(), 5, "VS code Installation", 30, "theory");
        addNewLesson(webDevelopment.getID(), section2.getID(), 6, "Remaster in progress", 30, "coding");
        writeCourseToFile(webDevelopment);
    }

    //COURSE OPERATIONS
    public Course writeCourseToFile(Course course) {
        fileHandler.writeCourse(course);
        return course;
    }

    public void readCourseFromFile(String fileReadOption) {
        fileHandler.readCourse(fileReadOption);
    }

    public Course createNewCourse(int id, String title, String authorName,
                                  String datePublished, double cost, double rating) {
        Course course = new Course(id, title, authorName,
                datePublished, cost, rating);
        getListOfCourses().add(course);
        return course;
    }

    public void removeCourseFromList(int courseID) {
        for (Course course : getListOfCourses()) {
            if (courseID == course.getID()) {
                getListOfCourses().remove(courseID - 1);
                return;
            }
        }
    }

    public boolean removeCourseFile(Path path) {
        return fileHandler.removeCourseFile(path);
    }

    public List<Course> getListOfCourses() {
        return courses;
    }

    public List<Path> getListOfCourseFiles(Path path) {
        return fileHandler.listCourseFiles(path);
    }

    public int getCourseCount() {
        return getListOfCourses().size();
    }

    public int getCourseFilesCount() {
        return getListOfCourseFiles(Paths.get(".")).size();
    }

    public void editCourseName(int courseID, String title) {
        var course = getListOfCourses().get(courseID - 1);
        course.setTitle(title);
    }

    public void printCourse(Course course) {
        System.out.printf("Course %d - %s", course.getID(), course.getTitle());
        for (Section section : course.getSections()) {
            System.out.printf("%n\tSection %d - %s", section.getID(), section.getTitle());
            for (Lesson lesson : section.getLessons()) {
                System.out.printf("%n\t\tLesson %d - %s (%d mins) (%s)%n", lesson.getID(), lesson.getTitle(), lesson.getDuration(), lesson.getType());
            }
        }
    }

    // SECTION OPERATIONS
    public Section addNewSection(int courseID, int sectionID, String title) {
        var section = new Section(sectionID, title);
        getListOfCourses().get(courseID - 1).addSection(section);
        return section;
    }

    public boolean removeSection(int courseID, int sectionID) {
        var sections = getListOfCourses().get(courseID - 1).getSections();
        if (sections.remove(sections.get(sectionID - 1))) {
            return true;
        }
        return false;
    }

    public void editSectionName(int courseID, int sectionID, String title) {
        getListOfCourses().get(courseID - 1).getSections()
                .get(sectionID - 1).setTitle(title);
    }

    public List<Section> getListOfSections(int courseID) {
        return getListOfCourses().get(courseID - 1).getSections();
    }

    public int getSectionsCount(int courseID) {
        return getListOfCourses().get(courseID - 1).getSections().size();
    }

    public Section getShortestSection(int courseID) {
        var sections = getListOfSections(courseID);
        sections.sort(new SortestSectionComparator());
        return sections.get(0);
    }

    public Section getLongestSection(int courseID) {
        var sections = getListOfSections(courseID);
        sections.sort(new SortestSectionComparator().reversed());
        return sections.get(0);
    }

    public List<Lesson> getLongestLesson(int courseID) {
        var sections = getListOfSections(courseID);
        List<Lesson> longestLessons = new ArrayList<>();
        for (Section section : sections) {
            var lessons = section.getLessons();
            lessons.sort(new LogestLessonComparator().reversed());
            longestLessons.add(lessons.get(0));
        }
        return longestLessons;
    }

    public Section getSectionWithMostLessons(int courseID) {
        var sections = getListOfSections(courseID);
        sections.sort(new SectionLessonComparator().reversed());
        return sections.get(0);
    }

    // LESSON OPERATIONS
    public Lesson addNewLesson(int courseID, int sectionID,
                               int lessonID, String title, int duration, String type) {
        if (type.toLowerCase().charAt(0) == 't') {
            Lesson lesson = new TheoryLesson(lessonID, title, duration);
            getListOfCourses().get(courseID - 1).getSections().get(sectionID - 1)
                    .addLesson(lesson);
            return lesson;
        }

        Lesson lesson = new CodingLesson(lessonID, title, duration);
        getListOfCourses().get(courseID - 1).getSections().get(sectionID - 1)
                .addLesson(lesson);
        return lesson;
    }

    public boolean removeLesson(int courseID, int sectionID,
                                int lessonID) {
        var lessons = getListOfCourses().get(courseID - 1).getSections().get(sectionID - 1).getLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getID() == lessonID) {
                if (lessons.remove(lesson)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void editLessonName(int courseID, int sectionID,
                               int lessonID, String newTitle) {
        getListOfLessonsFromSection(courseID, sectionID).get(lessonID - 1).setTitle(newTitle);
    }

    public List<Lesson> getListOfAllLessons(int courseID) {
        var sections = getListOfSections(courseID);
        List<Lesson> lessonsList = new ArrayList<>();
        for (Section section : sections) {
            lessonsList.addAll(section.getLessons());
        }
        return lessonsList;
    }

    public List<Lesson> getListOfLessonsFromSection(int courseID, int sectionID) {
        return getListOfSections(courseID).get(sectionID - 1).getLessons();
    }

    public List<Lesson> getLessonsWithSameKeyword(int courseID, String keyword) {
        var lessonsList = getListOfAllLessons(courseID);
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

    public int getLessonsCount(int courseID) {
        var sections = getListOfCourses().get(courseID - 1).getSections();
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


