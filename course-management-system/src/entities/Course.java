package entities;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseID;
    private String title;
    private String authorName;
    private String datePublished;
    private double cost;
    private double rating;
    private List<Section> sections;

    public Course(int courseID, String title, String authorName, String datePublished, double cost, double rating) {
        this.courseID = courseID;
        this.title = title;
        this.authorName = authorName;
        this.datePublished = datePublished;
        this.cost = cost;
        this.rating = rating;
        this.sections = new ArrayList<Section>();
    }

    public int getID() {
        return courseID;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

}
