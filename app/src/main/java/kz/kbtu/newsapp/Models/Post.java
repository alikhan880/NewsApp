package kz.kbtu.newsapp.Models;

/**
 * Created by abakh on 29-Sep-17.
 */

public class Post {
    private String message;
    private String userId;
    private long timestamp;

    public Post() {
    }

    public Post(String message, String userId, long timestamp) {
        this.message = message;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
