package kz.kbtu.newsapp.mvp.View;

/**
 * Created by abakh on 28-Sep-17.
 */

public interface AuthorizationView {
    void showLoading();
    void hideLoading();
    void showError(String s);
    void proceed();
}
