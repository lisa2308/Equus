package com.lisap.equus.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.R;
import com.lisap.equus.data.firestore.DbHorse;
import com.lisap.equus.databinding.ActivityMainBinding;
import com.lisap.equus.ui.login.LoginActivity;
import com.lisap.equus.ui.main.addupdatehorse.AddUpdateHorseActivity;
import com.lisap.equus.ui.main.details.HorseDetailsActivity;
import com.lisap.equus.ui.main.navdrawer.healthcares.HealthCareListActivity;
import com.lisap.equus.ui.main.notes.NoteListActivity;
import com.lisap.equus.ui.main.navdrawer.owners.OwnerListActivity;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.lisap.equus.data.preferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MainAdapter mainAdapter;
    private List<Horse> horseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setupToolbar();
        setupBottomNavigationView();
        initRecycler();
        setupSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.activityMainSearchView.closeSearch();
        loadRecyclerData("");
    }

    public void initRecycler(){
        RecyclerViewHolderListener listener = new RecyclerViewHolderListener() {
            @Override
            public void onItemClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                Horse horse = (Horse) item;
                Intent i = new Intent(MainActivity.this, HorseDetailsActivity.class);
                i.putExtra("horse", horse);
                startActivity(i);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemLongClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                Horse horse = (Horse) item;

                final FlatDialog flatDialog = new FlatDialog(MainActivity.this);
                flatDialog.setTitle("Options")
                    .setFirstButtonText("MODIFIER")
                    .setSecondButtonText("SUPPRIMER")
                        .setBackgroundColor(getColor(R.color.colorAccentLight))
                        .setFirstButtonColor(getColor(R.color.colorPrimaryLight))
                        .setSecondButtonColor(getColor(R.color.colordelete))
                        .setTitleColor(getColor(R.color.colorPrimaryDark))
                    .isCancelable(true)
                    .withFirstButtonListner(view -> {
                        startAddHorseActivity(horse);
                        flatDialog.dismiss();
                    })
                    .withSecondButtonListner(view -> {
                        DbHorse.deleteHorseDocument(
                                SharedPreferencesManager.getStable(MainActivity.this).getIdStable(),
                                horse.getHorseId()
                        ).addOnSuccessListener(documentReference -> {
                            Toast.makeText(MainActivity.this, "Cheval supprimé", Toast.LENGTH_LONG).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(MainActivity.this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                        });
                        loadRecyclerData("");
                        flatDialog.dismiss();
                    })
                    .show();
            }
        };

        mainAdapter = new MainAdapter(horseList, listener);
        binding.activityMainRecyclerView.setAdapter(mainAdapter);
        binding.activityMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.activityMainRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void loadRecyclerData(String query) {
        DbHorse.getHorseDocumentListSearch(
                SharedPreferencesManager.getStable(this).getIdStable(),
                query
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                // clear horse list
                horseList.clear();

                for (DocumentSnapshot document : task.getResult()) {
                    String uid = document.getId();
                    if (uid == null)
                        return;

                    Horse horse = document.toObject(Horse.class);
                    // add uid to note object
                    horse.setHorseId(uid);
                    horseList.add(horse);
                }
                mainAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setupToolbar() {
        binding.activityMainToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccentLight));
        setSupportActionBar(binding.activityMainToolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle("Equus");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        binding.activityMainNavView.setNavigationItemSelectedListener(this);
        TextView txtStableName = binding.activityMainNavView.getHeaderView(0).findViewById(R.id.activity_nav_drawer_header_txt_stable_name);
        txtStableName.setText(SharedPreferencesManager.getStable(this).getStableName());
    }

    public void setupBottomNavigationView(){
        binding.activityMainBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void setupSearch() {
        binding.activityMainSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("SimpleSearchView", "Text changed:" + query);
                loadRecyclerData(query);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });

        binding.activityMainSearchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {}

            @Override
            public void onSearchViewClosed() {
                loadRecyclerData("");
            }

            @Override
            public void onSearchViewShownAnimation() {}

            @Override
            public void onSearchViewClosedAnimation() {}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        binding.activityMainSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.activity_main_drawer_notif:
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_recap:
                startHealthCareListActivity();
                break;
            case R.id.activity_main_drawer_owners:
                startOwnerListActivity();
                break;
            case R.id.activity_main_drawer_logout:
                // unsubscribe from FCM topic
                FirebaseMessaging.getInstance().unsubscribeFromTopic(SharedPreferencesManager.getStable(this).getIdStable());

                SharedPreferencesManager.putStable(this, null);
                startLoginActivity();
                break;
            case R.id.bottom_main_add_horse:
                startAddHorseActivity(null);
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
                binding.activityMainDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }

    private void startHealthCareListActivity() {
        startActivity(new Intent(this, HealthCareListActivity.class));
    }

    private void startAddHorseActivity(Horse horse) {
        Intent intent = new Intent(this, AddUpdateHorseActivity.class);
        intent.putExtra("horse", horse);
        startActivity(intent);
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
    public void onBackPressed() {
        if (binding.activityMainSearchView.onBackPressed()) {
            return;
        }
    }
}
