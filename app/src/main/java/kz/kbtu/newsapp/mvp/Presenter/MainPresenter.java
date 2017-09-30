package kz.kbtu.newsapp.mvp.Presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void addOrDeleteFavorite(final Post p){
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(p.getId()).exists()){
                    dataSnapshot.getRef().child(p.getId()).setValue(null);
                }
                else{
                    dataSnapshot.getRef().child(p.getId()).setValue(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tag", "error");
            }
        });
    }


}
