package kz.kbtu.newsapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {


    @BindView(R.id.tv_header_add_post)
    TextView tvHeaderAddPost;
    @BindView(R.id.et_title_add_post)
    EditText etTitleAddPost;
    @BindView(R.id.cv_title_add_post)
    CardView cvTitleAddPost;
    @BindView(R.id.et_add_post)
    EditText etAddPost;
    Unbinder unbinder;
    View rootView;
    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_event, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
