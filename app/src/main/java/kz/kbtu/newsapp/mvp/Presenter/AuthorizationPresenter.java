package kz.kbtu.newsapp.mvp.Presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kz.kbtu.newsapp.Models.User;
import kz.kbtu.newsapp.mvp.View.AuthorizationView;

/**
 * Created by abakh on 28-Sep-17.
 */

public class AuthorizationPresenter {
    AuthorizationView view;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    public AuthorizationPresenter(AuthorizationView view) {
        this.view = view;
    }

    public void login(String email, String password){
        if(email.equals("") || password.equals("")){
            view.showError("Please, fill in all fields");
            return;
        }
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    view.proceed();
                }
                else{
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }

    public void register(String email, String password){
        if(email.equals("") || password.equals("")){
            view.showError("Please, fill in all fields");
            return;
        }
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            createUser();
                            view.proceed();
                        }
                        else{
                            view.showError(task.getException().getMessage());
                        }
                    }
                });
    }

    private void createUser(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User temp = new User(user.getUid().toString(), user.getEmail().toString());
        db.child("users").child(temp.getId()).setValue(temp);
    }
}
