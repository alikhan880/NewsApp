package kz.kbtu.newsapp.Fragment.Container;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kbtu.newsapp.Fragment.CatalogFragment;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogContainer extends Fragment {

    CatalogFragment fragment;
    FragmentManager fm;

    public CatalogContainer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initFragments();
        return inflater.inflate(R.layout.fragment_container, container, false);
    }


    private void initFragments(){
        fm = getChildFragmentManager();

        fragment = (CatalogFragment) fm.findFragmentByTag("CatalogFragment");
        if(fragment == null) {
            fragment = new CatalogFragment();
            fm.beginTransaction().replace(R.id.catalog_container, fragment, null).commit();
        }

    }



}
