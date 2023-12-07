package entities;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private static int LAST_ASSIGNED_ID = 0;
    private int staticID;
    private int courseNo;
    private String title;
    private String authorName;
    private String datePublished;
    private double cost;
    private double rating;
    private List<Section> sections;

    public Course(int courseNo, String title, String authorName, String datePublished, double cost, double rating) {
        this.staticID = ++LAST_ASSIGNED_ID;
        this.courseNo = courseNo;
        this.title = title;
        this.authorName = authorName;
        this.datePublished = datePublished;
        this.cost = cost;
        this.rating = rating;
        this.sections = new ArrayList<Section>();
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
