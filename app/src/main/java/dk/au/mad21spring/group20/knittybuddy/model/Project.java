package dk.au.mad21spring.group20.knittybuddy.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String Id;
    private String Name;
    private String Description;
    private int ImageId;
    private String Pdf;
    private boolean Published;
    private String UserId;
    private String ImageUrl;
    private List<String> StaredBy;


    public Project(){
        Id = "0"; //default value
        Name = "";
        Description = "";
        ImageId = 2131165326; //default value - app logo
        Pdf = "no pdf";
        Published = false; //a project is per default not published
        UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ImageUrl ="";
        StaredBy = new ArrayList<>();
    }

    public Project(String id, String name, String description, int imageId, String pdf, boolean published, String userId, String imageURL, List<String> staredBy){
        Id = id;
        Name = name;
        Description = description;
        ImageId = imageId;
        Pdf = pdf;
        Published = published;
        UserId = userId;
        ImageUrl = imageURL;
        StaredBy = staredBy;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageURL) {
        ImageUrl = imageURL;
    }

    public List<String> getStaredBy() {
        return StaredBy;
    }

    public void setStaredBy(List<String> staredBy) {
        StaredBy = staredBy;
    }

}
