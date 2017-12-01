package kz.kbtu.newsapp.Models;

/**
 * Created by abakh on 04-Oct-17.
 */

public class Comment {
    private String id;
    private User user;
    private String postId;
    private String message;
    private String timestamp;


    public Comment() {
    }

    public Comment(String id, User user, String postId, String message, String timestamp) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;
        if (postId != null ? !postId.equals(comment.postId) : comment.postId != null) return false;
        if (message != null ? !message.equals(comment.message) : comment.message != null)
            return false;
        return timestamp != null ? timestamp.equals(comment.timestamp) : comment.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
