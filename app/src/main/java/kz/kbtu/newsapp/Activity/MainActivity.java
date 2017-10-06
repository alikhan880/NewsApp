package kz.kbtu.newsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.newsapp.Adapter.RecyclerMainAdapter;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;
import kz.kbtu.newsapp.mvp.Presenter.MainPresenter;
import kz.kbtu.newsapp.mvp.View.MainView;

public class MainActivity extends AppCompatActivity implements RecyclerMainAdapter.RecyclerMainListener, MainView {
    ArrayList<Post> messagesList;
    ArrayList<Post> favorites;
    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerViewMain;
    RecyclerMainAdapter adapter;
    @BindView(R.id.progress_dialog)
    ProgressBar progressDialog;
    private DatabaseReference db;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        messagesList = new ArrayList<>();
        favorites = new ArrayList<>();
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerMainAdapter(messagesList, this, favorites);
        recyclerViewMain.setAdapter(adapter);
        presenter = new MainPresenter(this);
        setListener();
    }

    private void setListener() {
        showLoading();
        db = FirebaseDatabase.getInstance().getReference();
        ChildEventListener valueEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                Log.d("text", post.getMessage() + "");
                messagesList.add(post);
                adapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                messagesList.remove(post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ChildEventListener favsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                favorites.add(post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                favorites.remove(post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.child("posts").addChildEventListener(valueEventListener);
        db.child("favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addChildEventListener(favsListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_post:
                startActivity(new Intent(this, AddPostActivity.class));
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            default:
                return false;
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }


    @Override
    public void itemClicked(int position) {
        Post post = messagesList.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }

    @Override
    public void likeClicked(int position) {
        presenter.addOrDeleteFavorite(messagesList.get(position));
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        progressDialog.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressDialog.setVisibility(View.GONE);
    }

    @Override
    public void showError() {

    }
}
