package dk.au.mad21spring.group20.knittybuddy.Feed;

public class Feed {
    private String Topic;
    private String Difficilty;
    private String Description;
    private int OwnerId;

    public Feed(){}

    public Feed(String topic, String difficilty, String description, int ownerId) {
        Topic = topic;
        Difficilty = difficilty;
        Description = description;
        OwnerId = ownerId;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getDifficilty() {
        return Difficilty;
    }

    public void setDifficilty(String difficilty) {
        Difficilty = difficilty;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }
}
