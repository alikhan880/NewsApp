package kz.kbtu.newsapp.Fragment.Container;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kbtu.newsapp.Fragment.FavoritesFragment;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesContainer extends Fragment {

    FavoritesFragment fragment;
    FragmentManager fm;

    public FavoritesContainer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initFragments();
        return inflater.inflate(R.layout.fragment_favorites_container, container, false);
    }

    private void initFragments() {
        fm = getChildFragmentManager();

        fragment = (FavoritesFragment)fm.findFragmentByTag("FavoritesFragment");
        if(fragment == null){
            fragment = new FavoritesFragment();
            fm.beginTransaction().replace(R.id.favorite_container, fragment, null).commit();
        }
    }

}
