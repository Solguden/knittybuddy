package dk.au.mad21spring.group20.knittybuddy.model;

public class User {
    private String FullName;
    private String Email;
    private String UserId;

    public User(){};

    public User(String fullName, String email, String userId) {
        FullName = fullName;
        Email = email;
        UserId = userId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
