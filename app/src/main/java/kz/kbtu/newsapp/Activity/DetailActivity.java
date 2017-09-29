package kz.kbtu.newsapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

public class DetailActivity extends AppCompatActivity {
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        post = getIntent().getParcelableExtra("post");
    }
}
