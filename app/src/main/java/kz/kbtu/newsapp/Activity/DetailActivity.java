package kz.kbtu.newsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

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
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        post = getIntent().getParcelableExtra("post");
        loadData();
    }

    private void loadData() {
        tvTextDetail.setText(post.getMessage());
    }

    @OnClick(R.id.btn_show_comments)
    public void onViewClicked() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }
}
