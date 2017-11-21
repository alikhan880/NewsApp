package kz.kbtu.newsapp.Fragment;


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

import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
        db = FirebaseDatabase.getInstance();
        presenter = new SearchPresenter(this, db, posts);
        adapter = new RecyclerSearchAdapter(this, posts);
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

    }
}
