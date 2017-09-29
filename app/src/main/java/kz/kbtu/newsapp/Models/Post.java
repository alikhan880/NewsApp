package kz.kbtu.newsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abakh on 29-Sep-17.
 */

public class Post implements Parcelable {
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

    protected Post(Parcel in) {
        message = in.readString();
        userId = in.readString();
        timestamp = in.readLong();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(userId);
        dest.writeLong(timestamp);
    }
}
