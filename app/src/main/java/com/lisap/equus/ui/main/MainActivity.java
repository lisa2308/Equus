package com.lisap.equus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.ui.login.LoginActivity;
import com.lisap.equus.ui.main.navdrawer.notes.NoteListActivity;
import com.lisap.equus.ui.main.navdrawer.owner.OwnerListActivity;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.lisap.equus.ui.activities.HorsedetailsActivity;
import com.lisap.equus.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

// todo : securiser password hash
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.activity_main_recycler_view);
        toolbar = findViewById(R.id.activity_main_toolbar);
        mNavigationView = findViewById(R.id.activity_main_nav_view);
        drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);
        setUpToolbar();
        setUpBottomNavigationView();
        initRecycler();
    }

    public void initRecycler(){
        List<Horse> horseList = new ArrayList<>();
        //horseList.add(new Horse("https://i.ytimg.com/vi/-ql6xV-iTKc/maxresdefault.jpg", "Marco","Manon Breillet","0450251474"));
        //horseList.add(new Horse("https://i.ytimg.com/vi/-ql6xV-iTKc/maxresdefault.jpg", "Capri","Marie Ouannes","0625272824"));
        //ASSOCIATE ADAPTER WITH RECYCLER//
        mainAdapter = new MainAdapter(horseList, listener);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
    }

    public void setUpToolbar() {
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccentLight));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle("Equus");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    public void setUpBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    RecyclerViewHolderListener listener = new RecyclerViewHolderListener() {
        @Override
        public void onItemClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
            Horse h = (Horse) item;
            Intent i = new Intent(MainActivity.this, HorsedetailsActivity.class);
            i.putExtra("name", h.getName());
//            i.putExtra("proprietaire", h.getOwner());
//            i.putExtra("telproprio", h.getOwnerPhone());
            startActivity(i);
        }

        @Override
        public void onItemLongClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {}
    };

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.activity_main_drawer_owners:
                startOwnerListActivity();
                break;
            case R.id.activity_main_drawer_logout:
                SharedPreferencesManager.putStable(this, null);
                startLoginActivity();
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                return true;
            case R.id.menu_note:
                startNoteListActivity();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }

    private void startNoteListActivity() {
        startActivity(new Intent(this, NoteListActivity.class));
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void startOwnerListActivity() {
        startActivity(new Intent(this, OwnerListActivity.class));
    }

    @Override
    public void onBackPressed() {}
}
