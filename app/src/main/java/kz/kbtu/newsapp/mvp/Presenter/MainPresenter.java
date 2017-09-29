package kz.kbtu.newsapp.mvp.Presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.mvp.View.MainView;

/**
 * Created by abakh on 29-Sep-17.
 */

public class MainPresenter {
    MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void createPost(Post p){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("posts").push().setValue(p);
    }
}
