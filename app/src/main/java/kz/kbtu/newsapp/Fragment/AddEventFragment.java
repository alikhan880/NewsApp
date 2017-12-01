package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

import static android.app.Activity.RESULT_OK;

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
    @BindView(R.id.iv_add_event)
    ImageView ivAddEvent;
    @BindView(R.id.btn_pick_add_event)
    Button btnPickAddEvent;
    @BindView(R.id.btn_save_add_event)
    Button btnSaveAddEvent;
    Intent pickedData;


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

    private void flushThings(){
        etTitleAddPost.setText("");
        pickedData = null;
        etAddPost.setText("");
        ivAddEvent.setImageDrawable(getResources().getDrawable(R.drawable.logo));

    }


    @OnClick(R.id.btn_pick_add_event)
    public void onBtnPickAddEventClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101 ){
            if(resultCode == RESULT_OK){
                pickedData = data;
                ivAddEvent.setImageURI(data.getData());
            }
            else{
                pickedData = null;
                ivAddEvent.setImageDrawable(getResources().getDrawable(R.drawable.logo));
            }
        }
    }

    @OnClick(R.id.btn_save_add_event)
    public void onBtnSaveAddEventClicked() {
        String text = etAddPost.getText().toString().trim();
        String title = etTitleAddPost.getText().toString().trim();
        if (text.equals("") || title.equals("")) {
            Toast.makeText(getContext(), "Post or title has to be non-empty", Toast.LENGTH_SHORT).show();
            etAddPost.setText("");
            return;
        }
        Calendar calendar = Calendar.getInstance();
        long dateMs = calendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
        Date date = new Date(dateMs);
        String time = formatter.format(date);
        final String key = FirebaseDatabase.getInstance().getReference("posts")
                .push().getKey();
        final Post p = new Post(key, title, text, FirebaseAuth.getInstance().getCurrentUser().getUid(), time, null);
        FirebaseDatabase.getInstance()
                .getReference("posts")
                .child(key)
                .setValue(p);

        if(pickedData != null) {
            StorageReference ref = FirebaseStorage.getInstance()
                    .getReference(key);
            ref.putFile(pickedData.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Photo Uploaded", Toast.LENGTH_SHORT).show();
                    p.setImageURL(taskSnapshot.getDownloadUrl().toString());
                    FirebaseDatabase.getInstance().getReference("posts")
                            .child(key)
                            .setValue(p);
                    flushThings();
                }
            });

        }
        else{
            flushThings();
        }
    }
}

