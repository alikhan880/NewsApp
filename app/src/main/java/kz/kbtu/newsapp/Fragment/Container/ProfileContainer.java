package kz.kbtu.newsapp.Fragment.Container;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kbtu.newsapp.Fragment.ProfileFragment;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileContainer extends Fragment {
    ProfileFragment fragment;
    FragmentManager fm;

    public ProfileContainer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initFragments();
        return inflater.inflate(R.layout.fragment_profile_container, container, false);
    }

    private void initFragments() {
        fm = getChildFragmentManager();

        fragment = (ProfileFragment)fm.findFragmentByTag("ProfileFragment");
        if(fragment == null){
            fragment = new ProfileFragment();
            fm.beginTransaction().replace(R.id.profile_container, fragment, null).commit();
        }
    }

}
