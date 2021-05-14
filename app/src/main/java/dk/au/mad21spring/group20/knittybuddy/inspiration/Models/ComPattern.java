package dk.au.mad21spring.group20.knittybuddy.inspiration.Models;

import java.util.regex.Pattern;

public class ComPattern {
    public int id;
    public String name;
    public PatternAuthor pattern_author;
    public Photo first_photo;

    public ComPattern(int id, String name, PatternAuthor author, Photo photo) {
        this.id = id;
        this.name = name;
        this.pattern_author = author;
        this.first_photo = photo;
    }

    public Integer getId() {
        return id;
    }
    public String getName() { return name; }
    public PatternAuthor getAuthor() { return pattern_author; }
    public Photo getPhoto() { return first_photo; }

}
