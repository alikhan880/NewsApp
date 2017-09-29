package kz.kbtu.newsapp.mvp.Presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

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

    public void createPost(String text){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        String key = db.child("posts").push().getKey();
        db.child("posts").child(key).setValue(new Post(key,
                text,
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                Calendar.getInstance().getTimeInMillis()));
    }
}
