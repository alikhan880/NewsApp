package kz.kbtu.newsapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.kbtu.newsapp.Adapter.RecyclerCommentsAdapter;
import kz.kbtu.newsapp.Models.Comment;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.Models.User;
import kz.kbtu.newsapp.R;
import kz.kbtu.newsapp.mvp.Presenter.CommentsPresenter;
import kz.kbtu.newsapp.mvp.View.MainView;

public class CommentActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.recycler_view_comments)
    RecyclerView recyclerViewComments;
    @BindView(R.id.et_text_comments)
    EditText etTextComments;
    @BindView(R.id.btn_send_comment)
    Button btnSendComment;
    @BindView(R.id.progress_dialog)
    ProgressBar progressDialog;
    private Post post;
    private CommentsPresenter presenter;
    private FirebaseDatabase db;
    private ChildEventListener listener;
    private ArrayList<Comment> commentsList;
    private RecyclerCommentsAdapter adapter;


    @Override
    protected void onResume() {
        super.onResume();
        db.getReference().child("comments").child(post.getId()).addChildEventListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        post = getIntent().getParcelableExtra("post");
        presenter = new CommentsPresenter(this);
        db = FirebaseDatabase.getInstance();
        commentsList = new ArrayList<>();
        adapter = new RecyclerCommentsAdapter(commentsList);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(adapter);
        addListener();
    }



    @Override
    protected void onPause() {
        super.onPause();
        db.getReference().child("comments").child(post.getId()).removeEventListener(listener);
    }

    private void addListener() {
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                commentsList.add(comment);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                commentsList.remove(comment);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @OnClick(R.id.btn_send_comment)
    public void onViewClicked() {
        String message = etTextComments.getText().toString().trim();
        if (message.equals("")) {
            Toast.makeText(this, "Comment has to be non-empty", Toast.LENGTH_SHORT).show();
            return;
        }
        User curUser = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Comment comment = new Comment(null,
                curUser,
                post.getId(),
                message,
                Calendar.getInstance().getTimeInMillis());
        presenter.createComment(comment);
        etTextComments.setText("");
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
