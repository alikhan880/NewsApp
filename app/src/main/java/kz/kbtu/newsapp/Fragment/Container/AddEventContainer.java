package kz.kbtu.newsapp.Fragment.Container;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kbtu.newsapp.Fragment.AddEventFragment;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventContainer extends Fragment {

    AddEventFragment fragment;
    FragmentManager fm;

    public AddEventContainer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initFragments();
        return inflater.inflate(R.layout.fragment_add_event_container, container, false);
    }

    private void initFragments() {
        fm = getChildFragmentManager();

        fragment = (AddEventFragment) fm.findFragmentByTag("AddEventFragment");
        if(fragment == null){
            fragment = new AddEventFragment();
            fm.beginTransaction().replace(R.id.add_event_container, fragment, null).commit();
        }
    }

}
