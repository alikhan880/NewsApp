package kz.kbtu.newsapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
public class RegistrationFragment extends Fragment implements AuthorizationView {


    @BindView(R.id.et_email_login)
    TextInputEditText etEmailLogin;
    @BindView(R.id.et_password_login)
    TextInputEditText etPasswordLogin;
    @BindView(R.id.btn_enter_registration)
    Button btnEnterRegistration;
    @BindView(R.id.btn_back_registration)
    Button btnBackRegistration;
    Unbinder unbinder;
    AuthorizationPresenter presenter;
    @BindView(R.id.progress_dialog)
    ProgressBar progressDialog;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new AuthorizationPresenter(this);

        Bundle bundle = getArguments();
        if(bundle != null) placeInfo(bundle);
        
        return view;
    }

    private void placeInfo(Bundle b) {
        String email = b.getString("email");
        String password = b.getString("password");
        etEmailLogin.setText(email);
        etPasswordLogin.setText(password);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_enter_registration, R.id.btn_back_registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_enter_registration:
                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();
                presenter.register(email, password);
                break;
            case R.id.btn_back_registration:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
