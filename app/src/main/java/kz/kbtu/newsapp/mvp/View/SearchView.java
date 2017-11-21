package kz.kbtu.newsapp.mvp.View;

/**
 * Created by abakh on 21-Nov-17.
 */

public interface SearchView {
    void showLoading();
    void showError();
    void showList();
    void notifyAdapter();
}
