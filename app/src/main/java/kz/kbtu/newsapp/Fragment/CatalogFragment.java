package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kz.kbtu.newsapp.Activity.DetailActivity;
import kz.kbtu.newsapp.Adapter.RecyclerMainAdapter;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;
import kz.kbtu.newsapp.mvp.Presenter.MainPresenter;
import kz.kbtu.newsapp.mvp.View.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment implements MainView, RecyclerMainAdapter.RecyclerMainListener{
    View rootView;
    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerViewMain;
    @BindView(R.id.progress_dialog)
    ProgressBar progressDialog;
    Unbinder unbinder;
    ArrayList<Post> messagesList;
    ArrayList<Post> favorites;
    MainPresenter presenter;
    RecyclerMainAdapter adapter;
    DatabaseReference db;



    public CatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment", "onCreate2");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_catalog, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        messagesList = new ArrayList<>();
        favorites = new ArrayList<>();
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerMainAdapter(messagesList, this, favorites);
        recyclerViewMain.setAdapter(adapter);
        presenter = new MainPresenter(this);
        setListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    @Override
    public void itemClicked(int position) {
        Post post = messagesList.get(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
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


    private void setListener() {
        showLoading();
        db = FirebaseDatabase.getInstance().getReference();
        ChildEventListener valueEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                Log.d("text", post.getMessage() + "");
                messagesList.add(post);
                Collections.sort(messagesList, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        if(o1.getTimestamp() > o2.getTimestamp()) return -1;
                        else if(o1.getTimestamp() == o2.getTimestamp()) return 0;
                        else return 1;
                    }
                });
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

}
