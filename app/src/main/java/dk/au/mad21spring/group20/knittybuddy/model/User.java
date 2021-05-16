package dk.au.mad21spring.group20.knittybuddy.model;

import java.util.List;

public class User {
    private String FullName;
    private String Email;
    private String UserId;
    private List<String> UsersThisUserFollows;

    public User(){};

    public User(String fullName, String email, String userId, List<String> list) {
        FullName = fullName;
        Email = email;
        UserId = userId;
        UsersThisUserFollows = list;
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

    public List<String> getUsersThisUserFollows() { return UsersThisUserFollows; }

    public void setUsersThisUserFollows(List<String> list) { UsersThisUserFollows = list; }
}
