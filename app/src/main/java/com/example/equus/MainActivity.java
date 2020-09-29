package com.example.equus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.activity_main__recycler_view);
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
        horseList.add(new Horse("https://", "Marco","Marie","0450251474"));
        //ASSOCIATE ADAPTER WITH RECYCLER//
        recyclerViewAdapter = new RecyclerViewAdapter(horseList, listener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            i.putExtra("proprietaire", h.getProprietaire());
            i.putExtra("telproprio", h.getTelProprio());
            startActivity(i);
        }

    };

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
