package kz.kbtu.newsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by abakh on 29-Sep-17.
 */
public class Post implements Parcelable, Comparable<Post> {
    private String id;
    private String title;
    private String message;
    private String author;
    private String date;
    private String imageURL;

    public Post() {
    }

    public Post(String id, String title, String message, String userId, String date, String imageUrl) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.author = userId;
        this.date = date;
        this.imageURL = imageUrl;
    }


    protected Post(Parcel in) {
        id = in.readString();
        title = in.readString();
        message = in.readString();
        author = in.readString();
        date = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(imageURL);
    }

    @Override
    public int compareTo(@NonNull Post o) {
        return o.getDate().compareTo(this.getDate());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;

        Post post = (Post) o;

        if (id != null ? !id.equals(post.id) : post.id != null) return false;
        if (title != null ? !title.equals(post.title) : post.title != null) return false;
        if (message != null ? !message.equals(post.message) : post.message != null) return false;
        if (author != null ? !author.equals(post.author) : post.author != null) return false;
        if (date != null ? !date.equals(post.date) : post.date != null) return false;
        return imageURL != null ? imageURL.equals(post.imageURL) : post.imageURL == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (imageURL != null ? imageURL.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
