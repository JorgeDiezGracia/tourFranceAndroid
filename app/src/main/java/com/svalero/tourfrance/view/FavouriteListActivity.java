package com.svalero.tourfrance.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.adapter.FavouriteAdapter;
import com.svalero.tourfrance.database.AppDatabase;
import com.svalero.tourfrance.database.FavouriteCyclist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavouriteListActivity extends AppCompatActivity
        implements FavouriteAdapter.OnFavouriteClickListener {

    private RecyclerView recyclerView;
    private FavouriteAdapter adapter;
    private List<FavouriteCyclist> favouriteList = new ArrayList<>();
    private View tvEmpty;
    private AppDatabase db;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_favourites);
        tvEmpty = findViewById(R.id.tv_empty);
        FloatingActionButton fab = findViewById(R.id.fab_add_favourite);

        db = AppDatabase.getInstance(this);

        adapter = new FavouriteAdapter(this, favouriteList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, FavouriteFormActivity.class);
            startActivityForResult(intent, 1);
        });

        loadFavourites();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadFavourites();
        }
    }

    private void loadFavourites() {
        executor.execute(() -> {
            List<FavouriteCyclist> list = db.favouriteCyclistDao().getAll();
            handler.post(() -> {
                favouriteList.clear();
                favouriteList.addAll(list);
                adapter.setFavourites(favouriteList);
                if (favouriteList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);
                }
            });
        });
    }

    @Override
    public void onFavouriteClick(FavouriteCyclist favourite) {
        Intent intent = new Intent(this, FavouriteFormActivity.class);
        intent.putExtra("fav_id", favourite.getId());
        intent.putExtra("fav_name", favourite.getName());
        intent.putExtra("fav_specialty", favourite.getSpecialty());
        intent.putExtra("fav_birthplace", favourite.getBirthplace());
        intent.putExtra("fav_titles", favourite.getTitles());
        intent.putExtra("fav_notes", favourite.getNotes());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onFavouriteLongClick(FavouriteCyclist favourite) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar favorito")
                .setMessage("¿Seguro que quieres eliminar a " + favourite.getName() + "?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    executor.execute(() -> {
                        db.favouriteCyclistDao().delete(favourite);
                        handler.post(() -> {
                            Toast.makeText(this, "Favorito eliminado", Toast.LENGTH_SHORT).show();
                            loadFavourites();
                        });
                    });
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}