package kz.kbtu.newsapp.Models;

/**
 * Created by abakh on 22-Sep-17.
 */

public class Message {
    private User user;
    private String message;
    private long timestamp;

    public Message(User user, String message, long timestamp) {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
