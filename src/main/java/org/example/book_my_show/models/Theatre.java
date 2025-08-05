package org.example.book_my_show.models;

import java.util.List;

public class Theatre {
    private String name;
    private String address;
    private List<Screen> screens;

    // Missed:
    private City city; // used if there is a requirement to search by theatre name, and selecting user's city

}
