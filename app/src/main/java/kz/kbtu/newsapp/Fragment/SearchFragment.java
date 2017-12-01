package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.kbtu.newsapp.Activity.DetailActivity;
import kz.kbtu.newsapp.Adapter.RecyclerSearchAdapter;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;
import kz.kbtu.newsapp.mvp.Presenter.SearchPresenter;
import kz.kbtu.newsapp.mvp.View.SearchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView, RecyclerSearchAdapter.RecyclerSearchListener{

    View rootView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerViewSearch;
    Unbinder unbinder;
    String searchText;
    SearchPresenter presenter;
    Vector<Post> posts;
    Vector<Post> favorites;
    FirebaseDatabase db;
    RecyclerSearchAdapter adapter;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        posts = new Vector<>();
        favorites = new Vector<>();
        db = FirebaseDatabase.getInstance();
        presenter = new SearchPresenter(this, db, posts, favorites);
        adapter = new RecyclerSearchAdapter(this, posts, favorites);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        searchText = etSearch.getText().toString().trim();
        if(!searchText.equals("")){
            presenter.makeSearch(searchText);
        }else{
            Toast.makeText(getContext(), "Search text have to be non-empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showList() {

    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void itemClicked(int position) {
        Post post = posts.get(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }

    @Override
    public void likeClicked(int position) {
        final Post p = posts.get(position);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("favorites")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(uid).exists()){
                            if(dataSnapshot.child(uid).child(p.getId()).exists()){
                                dataSnapshot.getRef().child(uid).child(p.getId()).setValue(null);
                                favorites.remove(p);
                                adapter.notifyDataSetChanged();
                            }
                            else{
                                dataSnapshot.getRef().child(uid).child(p.getId()).setValue(p);
                                favorites.add(p);
                                Collections.sort(favorites);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        else{
                            DatabaseReference ref = dataSnapshot.getRef();
                            ref.child(uid).child(p.getId()).setValue(p);
                            favorites.add(p);
                            Collections.sort(favorites);
                            adapter.notifyDataSetChanged();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
