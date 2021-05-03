package dk.au.mad21spring.group20.knittybuddy.Project;

public class Project {
    private String Id;
    private String Name;
    private String Description;
    private String ImageId;
    private String Pdf;

    public Project(){}

    public Project(String id, String name, String description, String imageId, String pdf){
        Id = id;
        Name = name;
        Description = description;
        ImageId = imageId;
        Pdf = pdf;
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

    public String setPdf() {
        return Pdf;
    }

    public void setPdf(String pdf) {
        Pdf = pdf;
    }

}
