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
    /**
     * Assign Auto-generated id to course.
     */
    private int id;
    /**
     * Get course id - get as input.
     */
    private int courseId;
    /**
     * Course Title
     */
    private String title;
    /**
     * Course Author Name
     */
    private String authorName;
    /**
     * Course Date Published
     */
    private Instant datePublished;
    /**
     * Course Price
     */
    private double price;
    /**
     * Course Rating
     */
    private double rating;
    /**
     * Sections list - stores section objects
     */
    private List<Section> sections;
    /**
     * Random to generate random rating between 1 -5
     */
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

    /**
     * Get auto-generated course id
     */
    public int getId() {
        return id;
    }

    /**
     * Get course id
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Set course id
     */
    public void setCourseId(int newCourseId) {
        this.courseId = newCourseId;
    }

    /**
     * Get course Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set course Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Course Author's name
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Set Course Author's name
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Get Course price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Course price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get Course rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Set Course rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    public Instant getDatePublished() {
        return datePublished;
    }

    /**
     * Converts a String date to {@link Instant}.
     *
     * @param dateString date string in 'dd-MM-yyy' format
     * @return Instant
     */
    private Instant convertInstant(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    /**
     * Converts and set a new String date to {@link Instant}.
     *
     * @param dateString date string in 'dd-MM-yyy' format
     */
    public void setDatePublished(String dateString) {
        this.datePublished = convertInstant(dateString);
    }

    /**
     * Get sections list
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * Add section to sections list
     */
    public void addSection(Section section) {
        sections.add(section);
    }

}
