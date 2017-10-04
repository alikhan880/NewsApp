package kz.kbtu.newsapp.mvp.Presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kz.kbtu.newsapp.Models.Comment;
import kz.kbtu.newsapp.mvp.View.MainView;

/**
 * Created by abakh on 04-Oct-17.
 */

public class CommentsPresenter {
    private MainView view;


    public CommentsPresenter(MainView view) {
        this.view = view;
    }

    public void createComment(Comment c){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        String key = db.child("comments").child(c.getPostId()).push().getKey();
        c.setId(key);
        db.child("comments").child(c.getPostId()).child(key).setValue(c);
    }
}
