package kz.kbtu.newsapp.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.newsapp.Fragment.Container.AddEventContainer;
import kz.kbtu.newsapp.Fragment.Container.CatalogContainer;
import kz.kbtu.newsapp.Fragment.Container.FavoritesContainer;
import kz.kbtu.newsapp.Fragment.Container.ProfileContainer;
import kz.kbtu.newsapp.Fragment.Container.SearchContainer;
import kz.kbtu.newsapp.R;

public class BottomNavigationActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    FragmentManager fm;
    CatalogContainer catalogContainer;
    SearchContainer searchContainer;
    AddEventContainer addEventContainer;
    FavoritesContainer favoritesContainer;
    ProfileContainer profileContainer;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        fm = getSupportFragmentManager();
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initContainers();
        addListener();
        requestPermission();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initContainers() {
        catalogContainer = (CatalogContainer) fm.findFragmentByTag("Catalog");
        if(catalogContainer == null) catalogContainer = new CatalogContainer();
        fm.beginTransaction().add(R.id.main_container, catalogContainer).hide(catalogContainer).commit();

        searchContainer = (SearchContainer) fm.findFragmentByTag("Search");
        if(searchContainer == null) searchContainer = new SearchContainer();
        fm.beginTransaction().add(R.id.main_container, searchContainer).hide(searchContainer).commit();

        addEventContainer = (AddEventContainer)fm.findFragmentByTag("Event");
        if(addEventContainer == null) addEventContainer = new AddEventContainer();
        fm.beginTransaction().add(R.id.main_container, addEventContainer).hide(addEventContainer).commit();

        favoritesContainer = (FavoritesContainer)fm.findFragmentByTag("Favorites");
        if(favoritesContainer == null) favoritesContainer = new FavoritesContainer();
        fm.beginTransaction().add(R.id.main_container, favoritesContainer).hide(favoritesContainer).commit();

        profileContainer = (ProfileContainer)fm.findFragmentByTag("Profile");
        if(profileContainer == null) profileContainer = new ProfileContainer();
        fm.beginTransaction().add(R.id.main_container, profileContainer).hide(profileContainer).commit();

    }

    private void addListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_catalog:
                        fm.beginTransaction()
                                .hide(searchContainer)
                                .hide(addEventContainer)
                                .hide(favoritesContainer)
                                .hide(profileContainer)
                                .show(catalogContainer)
                                .commit();
                        return true;
                    case R.id.menu_search:
                        fm.beginTransaction()
                                .hide(catalogContainer)
                                .hide(addEventContainer)
                                .hide(favoritesContainer)
                                .hide(profileContainer)
                                .show(searchContainer)
                                .commit();
                        return true;
                    case R.id.menu_add:
                        fm.beginTransaction()
                                .hide(searchContainer)
                                .hide(catalogContainer)
                                .hide(favoritesContainer)
                                .hide(profileContainer)
                                .show(addEventContainer)
                                .commit();
                        return true;
                    case R.id.menu_favorites:
                        fm.beginTransaction()
                                .hide(searchContainer)
                                .hide(addEventContainer)
                                .hide(catalogContainer)
                                .hide(profileContainer)
                                .show(favoritesContainer)
                                .commit();
                        return true;
                    case R.id.menu_profile:
                        fm.beginTransaction()
                                .hide(searchContainer)
                                .hide(addEventContainer)
                                .hide(favoritesContainer)
                                .hide(catalogContainer)
                                .show(profileContainer)
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
        bottomNavigation.setSelectedItemId(R.id.menu_catalog);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        item = menu.findItem(R.id.menu_send);
        return true;
    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_NETWORK_STATE}, 10);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                } else {
                    super.onBackPressed();
                }
            }
        }
    }
}
