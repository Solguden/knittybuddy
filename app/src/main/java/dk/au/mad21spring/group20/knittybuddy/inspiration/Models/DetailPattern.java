package dk.au.mad21spring.group20.knittybuddy.inspiration.Models;

public class DetailPattern {
    public int id;
    public String patternName;
    public String patternPhoto;
    public String authorName;
    public String authorPhoto;
//    public User author;

    public DetailPattern() {}

    public DetailPattern(int id, String patternName, String patternPhoto, String authorName, String authorPhoto ) { //  User author
        this.id = id;
        this.patternName = patternName;
        this.patternPhoto = patternPhoto;
//        this.author = author;
        this.authorName = authorName;
        this.authorPhoto = authorPhoto;
    }

    public Integer getId() {
        return id;
    }
    public String getPatternName() { return patternName; }
    public String getPatternPhoto() { return patternPhoto; }
//    public User getAuthor() { return author;}
    public String getAuthorName() { return authorName; }
    public String getAuthorPhoto() { return authorPhoto; }
}
