package kz.kbtu.newsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements RecyclerMainAdapter.RecyclerMainListener{
    ArrayList<Post> messagesList;
    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerViewMain;
    RecyclerMainAdapter adapter;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        messagesList = new ArrayList<>();
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerMainAdapter(messagesList, this);
        recyclerViewMain.setAdapter(adapter);
        setListener();
    }

    private void setListener() {
        db = FirebaseDatabase.getInstance().getReference();
        ChildEventListener valueEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(MainActivity.this, "added", Toast.LENGTH_SHORT).show();
                Post post = dataSnapshot.getValue(Post.class);
                Log.d("text", post.getMessage() + "");
                messagesList.add(post);
                adapter.notifyDataSetChanged();
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
        db.child("posts").addChildEventListener(valueEventListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_post:
                startActivity(new Intent(this, AddPostActivity.class));
                return true;
            default:return false;
        }
    }

    @Override
    public void itemClicked(int position) {
        Post post = messagesList.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }
}
