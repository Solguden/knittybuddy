package dk.au.mad21spring.group20.knittybuddy.project;

public class Project {
    private String Id;
    private String Name;
    private String Description;
    private String ImageId;
    private String Pdf;
    private boolean Published;

    public Project(){
        Published = false;
    }

    public Project(String id, String name, String description, String imageId, String pdf, boolean published){
        Id = id;
        Name = name;
        Description = description;
        ImageId = imageId;
        Pdf = pdf;
        Published = published;
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

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
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

}
