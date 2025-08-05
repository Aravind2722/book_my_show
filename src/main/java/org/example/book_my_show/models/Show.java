package org.example.book_my_show.models;

import java.util.Date;
import java.util.List;

public class Show {
    private String name;
    private Date startTime;
    private Date endTime;
    private Movie movie;
    private Screen screen;
    private List<Feature> features;

    // Missed:
    private Language language;
}
