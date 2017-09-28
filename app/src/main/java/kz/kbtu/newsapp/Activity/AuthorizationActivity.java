package kz.kbtu.newsapp.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import kz.kbtu.newsapp.Fragment.LoginFragment;
import kz.kbtu.newsapp.R;


public class AuthorizationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_authorization, new LoginFragment()).commit();
    }


}
