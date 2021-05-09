package dk.au.mad21spring.group20.knittybuddy.model;

import com.google.firebase.firestore.auth.User;

public class Project {
    private String Id;
    private String Name;
    private String Description;
    private int ImageId;
    private String Pdf;
    private boolean Published;
    private String UserId;

    public Project(){
        Published = false;
    }

    public Project(String id, String name, String description, int imageId, String pdf, boolean published, String userId){
        Id = id;
        Name = name;
        Description = description;
        ImageId = imageId;
        Pdf = pdf;
        Published = published;
        UserId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getPdf() {
        return Pdf;
    }

    public void setPdf(String pdf) {
        Pdf = pdf;
    }

    public boolean getPublished() { return Published; }

    public void setPublished(boolean published) { Published = published; }

    public String getUserId() { return UserId; }

    public void setUserId(String userId) { UserId = userId; }

}
