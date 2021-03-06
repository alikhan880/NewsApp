package kz.kbtu.newsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_text_detail)
    TextView tvTextDetail;
    @BindView(R.id.btn_show_comments)
    Button btnShowComments;
    @BindView(R.id.tv_title_detail)
    TextView tvTitleDetail;
    @BindView(R.id.iv_post_detail)
    ImageView ivPostDetail;
    @BindView(R.id.iv_post_detail_container)
    CardView ivPostDetailContainer;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        post = getIntent().getParcelableExtra("post");
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    private void loadData() {
        tvTextDetail.setText(post.getMessage());
        tvTitleDetail.setText(post.getTitle());
        if(post.getImageURL() != null && !post.getImageURL().equals("")){
            Picasso.with(this)
                    .load(post.getImageURL())
                    .fit()
                    .centerCrop()
                    .into(ivPostDetail);
            ivPostDetailContainer.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_show_comments)
    public void onViewClicked() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }
}
