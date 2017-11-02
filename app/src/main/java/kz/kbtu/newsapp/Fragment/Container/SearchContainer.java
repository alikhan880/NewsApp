package kz.kbtu.newsapp.Fragment.Container;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kbtu.newsapp.Fragment.SearchFragment;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchContainer extends Fragment {

    SearchFragment fragment;
    FragmentManager fm;

    public SearchContainer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initFragments();
        return inflater.inflate(R.layout.fragment_search_container, container, false);
    }

    private void initFragments() {
        fm = getChildFragmentManager();

        fragment = (SearchFragment) fm.findFragmentByTag("SearchFragment");
        if(fragment == null){
            fragment = new SearchFragment();
            fm.beginTransaction().replace(R.id.search_container, fragment, null).commit();
        }
    }

}
