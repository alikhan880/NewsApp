package kz.kbtu.newsapp.mvp.Presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import info.debatty.java.stringsimilarity.Damerau;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.mvp.View.SearchView;

/**
 * Created by abakh on 21-Nov-17.
 */

public class SearchPresenter {
    SearchView view;
    FirebaseDatabase db;
    Vector<Post> posts;
    Damerau damerau;

    public SearchPresenter(SearchView view, FirebaseDatabase db, Vector<Post> posts) {
        this.view = view;
        this.db = db;
        this.posts = posts;
        damerau = new Damerau();
    }

    public void makeSearch(final String text){
        posts.clear();
        DatabaseReference ref = db.getReference("posts");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    Log.d("casted", post.toString());
                    String[] wordsFrom = text.split("\\s+");
                    String postText = post.getTitle() + " " + post.getMessage();
                    String[] wordsTo = postText.split("\\s+");
                    for (String aWordsFrom : wordsFrom) {
                        boolean check = false;
                        for (String aWordsTo : wordsTo) {
                            Log.d("damerau", "value:" + damerau.distance(aWordsFrom, aWordsTo));
                            if (damerau.distance(aWordsFrom, aWordsTo) < 3) {
                                Log.d("distances", aWordsFrom + " : " + aWordsTo);
                                posts.add(post);
                                check = true;
                                break;
                            }
                        }
                        if(check) break;
                    }
                }
                view.notifyAdapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
