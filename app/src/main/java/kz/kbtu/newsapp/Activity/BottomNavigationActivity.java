package kz.kbtu.newsapp.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        fm = getSupportFragmentManager();
        ButterKnife.bind(this);
        initContainers();
        addListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initContainers() {
        catalogContainer = (CatalogContainer) fm.findFragmentByTag("Catalog");
        if(catalogContainer == null) catalogContainer = new CatalogContainer();

        searchContainer = (SearchContainer) fm.findFragmentByTag("Search");
        if(searchContainer == null) searchContainer = new SearchContainer();

        addEventContainer = (AddEventContainer)fm.findFragmentByTag("Event");
        if(addEventContainer == null) addEventContainer = new AddEventContainer();

        favoritesContainer = (FavoritesContainer)fm.findFragmentByTag("Favorites");
        if(favoritesContainer == null) favoritesContainer = new FavoritesContainer();

        profileContainer = (ProfileContainer)fm.findFragmentByTag("Profile");
        if(profileContainer == null) profileContainer = new ProfileContainer();

    }

    private void addListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_catalog:
                        fm.beginTransaction().replace(R.id.main_container, catalogContainer, null).commit();
                        Toast.makeText(BottomNavigationActivity.this, "Catalog", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_search:
                        fm.beginTransaction().replace(R.id.main_container, searchContainer, null).commit();
                        Toast.makeText(BottomNavigationActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_add:
                        fm.beginTransaction().replace(R.id.main_container, addEventContainer, null).commit();
                        Toast.makeText(BottomNavigationActivity.this, "Add event", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_favorites:
                        fm.beginTransaction().replace(R.id.main_container, favoritesContainer, null).commit();
                        Toast.makeText(BottomNavigationActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        fm.beginTransaction().replace(R.id.main_container, profileContainer, null).commit();
                        Toast.makeText(BottomNavigationActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        bottomNavigation.setSelectedItemId(R.id.menu_catalog);
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
