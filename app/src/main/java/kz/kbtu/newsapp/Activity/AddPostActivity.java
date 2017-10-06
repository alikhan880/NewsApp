package kz.kbtu.newsapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.newsapp.R;
import kz.kbtu.newsapp.mvp.Presenter.MainPresenter;
import kz.kbtu.newsapp.mvp.View.MainView;

public class AddPostActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.et_add_post)
    EditText etAddPost;
    @BindView(R.id.et_title_add_post)
    EditText etTitleAddPost;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new MainPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:
                createPost();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    private void createPost() {
        String text = etAddPost.getText().toString().trim();
        String title = etTitleAddPost.getText().toString().trim();
        if (text.equals("") || title.equals("")) {
            Toast.makeText(this, "Post or title has to be non-empty", Toast.LENGTH_SHORT).show();
            etAddPost.setText("");
            return;
        }
        presenter.createPost(title,text);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }
}
