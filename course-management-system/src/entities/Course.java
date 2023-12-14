package entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Course model.
 */
public class Course {
    /**
     * Auto-generated id for each new course.
     */
    private static int NEXT_ID = 0;
    private int id;
    private int courseId;
    private String title;
    private String authorName;
    private Instant datePublished;
    private double price;
    private double rating;
    private List<Section> sections;
    private Random random = new Random();

    public Course(int courseId, String title, String authorName, String datePublished, double price) {
        this.id = ++NEXT_ID;
        this.courseId = courseId;
        this.title = title;
        this.authorName = authorName;
        this.datePublished = convertInstant(datePublished);
        this.price = price;
        this.rating = 1 + (random.nextDouble() * (5 - 1));
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    /**
     * Converts a String date to {@link Instant}.
     *
     * @param dateStr date string in 'dd-MM-yyy' format
     * @return Instant
     */
    private Instant convertInstant(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
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
