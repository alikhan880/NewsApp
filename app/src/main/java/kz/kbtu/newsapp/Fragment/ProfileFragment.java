package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.kbtu.newsapp.Models.User;
import kz.kbtu.newsapp.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    Unbinder unbinder;
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
                        Picasso.with(getContext()).load(user.getPhotoUrl())
                                .fit().centerCrop().into(ivProfile);
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

    @OnClick(R.id.iv_profile)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 101);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            final Uri uri = data.getData();
            StorageReference ref = FirebaseStorage.getInstance()
                    .getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Upload success", Toast.LENGTH_SHORT).show();
                    Log.d("image", taskSnapshot.getDownloadUrl() + "");
                    updatePhotoUrl(taskSnapshot.getDownloadUrl());
                }
            });
        }
    }

    private void updatePhotoUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User temp = new User(user.getUid(), user.getEmail(), uri.toString());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(user.getUid()).setValue(temp);
        Picasso.with(getContext()).load(temp.getPhotoUrl()).fit().centerCrop().into(ivProfile);
    }
}
