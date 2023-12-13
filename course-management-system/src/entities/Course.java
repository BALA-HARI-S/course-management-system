package entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Random random = new Random();

    public Course(int courseId, String title, String authorName, String datePublished, double cost) {
        this.id = ++NEXT_ID;
        this.courseId = courseId;
        this.title = title;
        this.authorName = authorName;
        this.datePublished = convertInstant(datePublished);
        this.cost = cost;
        this.rating = random.nextDouble() * 5;
        this.sections = new ArrayList<Section>();
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int newCourseId) {
        this.courseId = newCourseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
