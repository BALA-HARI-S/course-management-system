package entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private static int NEXT_ID = 0;
    private int id;
    private int courseId;
    private String title;
    private String authorName;
    private Instant datePublished;
    private double cost;
    private double rating;
    private List<Section> sections;

    public Course(int courseId, String title, String authorName, String datePublished, double cost, double rating) {
        this.id = ++NEXT_ID;
        this.courseId = courseId;
        this.title = title;
        this.authorName = authorName;
        this.datePublished = convertInstant(datePublished);
        this.cost = cost;
        this.rating = rating;
        this.sections = new ArrayList<Section>();
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getDatePublished() {
        return datePublished;
    }

    private Instant convertInstant(String userInput) {
        LocalDate localDate = LocalDate.parse(userInput, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant;
    }

    public void setDatePublished(Instant datePublished) {
        this.datePublished = datePublished;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

}
