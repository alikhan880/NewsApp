package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kz.kbtu.newsapp.Activity.DetailActivity;
import kz.kbtu.newsapp.Adapter.RecyclerFavoritesAdapter;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements RecyclerFavoritesAdapter.FavoritesAdapterListener{


    @BindView(R.id.recycler_favorites)
    RecyclerView recyclerFavorites;
    Unbinder unbinder;
    ArrayList<Post> favoritesList;
    RecyclerFavoritesAdapter adapter;
    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        unbinder = ButterKnife.bind(this, view);
        favoritesList = new ArrayList<>();
        adapter = new RecyclerFavoritesAdapter(favoritesList, this);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerFavorites.setAdapter(adapter);
        getData();
        return view;
    }

    private void getData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                favoritesList.add(post);
                //notify adapter
                Collections.sort(favoritesList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                favoritesList.remove(post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void itemClicked(int position) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("post", favoritesList.get(position));
        startActivity(intent);
    }

    @Override
    public void likeClicked(int position) {
        final Post p = favoritesList.get(position);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("favorites")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(uid).exists()){
                            if(dataSnapshot.child(uid).child(p.getId()).exists()){
                                dataSnapshot.getRef().child(uid).child(p.getId()).setValue(null);
                                favoritesList.remove(p);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
