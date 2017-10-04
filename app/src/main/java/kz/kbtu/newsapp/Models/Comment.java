package kz.kbtu.newsapp.Models;

/**
 * Created by abakh on 04-Oct-17.
 */

public class Comment {
    private String id;
    private User user;
    private String postId;
    private String message;
    private long timestamp;


    public Comment(String id, User user, String postId, String message, long timestamp) {
        this.id = id;
        this.user = user;
        this.postId = postId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Comment() {

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) return false;
        if(this == obj) return true;
        return this.getId().equals(((Comment)obj).getId());
    }
}
