package kz.kbtu.newsapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.newsapp.Models.Post;
import kz.kbtu.newsapp.R;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_text_detail)
    TextView tvTextDetail;
    @BindView(R.id.recycler_view_detail)
    RecyclerView recyclerViewDetail;
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
}
