package kz.kbtu.newsapp.Models;

/**
 * Created by abakh on 22-Sep-17.
 */

public class User {
    private String id;
    private String email;
    private String photoUrl;
    private String name;

    public User() {

    }

    public User(String id, String email, String photoUrl, String name) {
        this.id = id;
        this.email = email;
        this.photoUrl = photoUrl;
        this.name = name;
    }

    public User(String id, String email, String photoUrl) {
        this(id, email, photoUrl, "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
