package kz.kbtu.newsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abakh on 29-Sep-17.
 */
public class Post implements Parcelable {
    private String id;
    private String title;
    private String message;
    private String userId;
    private long timestamp;
    private int cnt;

    public Post() {
    }

    public Post(String id, String title, String message, String userId, long timestamp, int cnt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.userId = userId;
        this.timestamp = timestamp;
        this.cnt = cnt;
    }

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

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    protected Post(Parcel in) {
        id = in.readString();
        title = in.readString();
        message = in.readString();
        userId = in.readString();
        timestamp = in.readLong();
        cnt = in.readInt();
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) return false;
        if(this == obj) return true;
        return this.getId().equals(((Post)obj).getId());
    }



    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("message", message);
        result.put("userId", userId);
        result.put("timestamp", timestamp);
        result.put("cnt", cnt);
        return result;
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
        dest.writeString(userId);
        dest.writeLong(timestamp);
        dest.writeInt(cnt);
    }
}
