package kz.kbtu.newsapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.newsapp.Adapter.RecyclerMainAdapter;
import kz.kbtu.newsapp.Models.Message;
import kz.kbtu.newsapp.R;

public class MainActivity extends AppCompatActivity {
    ArrayList<Message> messagesList;
    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerViewMain;
    RecyclerMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        messagesList = new ArrayList<>();
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerMainAdapter(messagesList);
        getMessages();
    }

    private void getMessages() {
    }


}
