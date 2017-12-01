package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.kbtu.newsapp.Activity.BottomNavigationActivity;
import kz.kbtu.newsapp.R;
import kz.kbtu.newsapp.mvp.Presenter.AuthorizationPresenter;
import kz.kbtu.newsapp.mvp.View.AuthorizationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements AuthorizationView {

    @BindView(R.id.et_email_login)
    TextInputEditText etEmailLogin;
    @BindView(R.id.et_password_login)
    TextInputEditText etPasswordLogin;
    @BindView(R.id.btn_enter_login)
    Button btnEnterLogin;
    Unbinder unbinder;
    AuthorizationPresenter presenter;
    @BindView(R.id.progress_dialog)
    ProgressBar progressDialog;
    @BindView(R.id.tv_register_login)
    TextView tvRegisterLogin;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new AuthorizationPresenter(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_enter_login, R.id.tv_register_login, R.id.tv_forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_enter_login:
                String email = etEmailLogin.getText().toString().trim();
                String password = etPasswordLogin.getText().toString().trim();
                presenter.login(email, password);
                break;
            case R.id.tv_register_login:
                registerClicked();
                break;

            case R.id.tv_forgot_password:

                break;
        }
    }

    private void registerClicked() {
        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("password", password);
        RegistrationFragment f = new RegistrationFragment();
        f.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.container_authorization, f)
                .addToBackStack(null).commit();
    }

    @Override
    public void showLoading() {
        progressDialog.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressDialog.setVisibility(View.GONE);
    }

    @Override
    public void showError(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void proceed() {
        getActivity().startActivity(new Intent(getActivity(), BottomNavigationActivity.class));
        getActivity().finish();
    }

}
