package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.kbtu.newsapp.Activity.AuthorizationActivity;
import kz.kbtu.newsapp.Models.User;
import kz.kbtu.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    Unbinder unbinder;
    @BindView(R.id.tv_name_profile)
    TextView tvNameProfile;
    @BindView(R.id.tv_location_profile)
    TextView tvLocationProfile;
    @BindView(R.id.tv_email_profile)
    TextView tvEmailProfile;
    @BindView(R.id.btn_profile_settings)
    Button btnProfileSettings;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;
    private View rootView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        Log.d("image", FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() + "");
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user.getPhotoUrl() != null && !user.getPhotoUrl().equals("")){
                            Picasso.with(getContext()).load(user.getPhotoUrl())
                                    .fit().centerCrop().into(ivProfile);
                        }
                        else{
                            ivProfile.setImageResource(R.drawable.logo);
                        }

                        if(user.getName() != null && !user.getName().equals("")){
                            tvNameProfile.setText(user.getName());
                        }
                        else{
                            tvNameProfile.setText("Set your name in settings");
                        }

                        tvEmailProfile.setText(user.getEmail());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btn_profile_settings, R.id.btn_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_profile_settings:
                getFragmentManager().beginTransaction().replace(R.id.profile_container, new ProfileSettingsFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.btn_sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), AuthorizationActivity.class));
                getActivity().finish();
                break;
        }
    }
}
