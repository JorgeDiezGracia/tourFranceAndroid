package com.svalero.tourfrance.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.database.AppDatabase;
import com.svalero.tourfrance.database.FavouriteCyclist;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavouriteFormActivity extends AppCompatActivity {

    private TextInputEditText etName, etSpecialty, etBirthplace, etTitles, etNotes;
    private AppDatabase db;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private int favId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_form);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etName = findViewById(R.id.et_fav_name);
        etSpecialty = findViewById(R.id.et_fav_specialty);
        etBirthplace = findViewById(R.id.et_fav_birthplace);
        etTitles = findViewById(R.id.et_fav_titles);
        etNotes = findViewById(R.id.et_fav_notes);
        MaterialButton btnSave = findViewById(R.id.btn_save_fav);

        db = AppDatabase.getInstance(this);

        if (getIntent().hasExtra("fav_id")) {
            favId = getIntent().getIntExtra("fav_id", -1);
            etName.setText(getIntent().getStringExtra("fav_name"));
            etSpecialty.setText(getIntent().getStringExtra("fav_specialty"));
            etBirthplace.setText(getIntent().getStringExtra("fav_birthplace"));
            etTitles.setText(String.valueOf(getIntent().getIntExtra("fav_titles", 0)));
            etNotes.setText(getIntent().getStringExtra("fav_notes"));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.edit_favourite_title);
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.add_favourite_title);
            }
        }

        btnSave.setOnClickListener(v -> saveFavourite());
    }

    private void saveFavourite() {
        String name = etName.getText().toString().trim();
        String specialty = etSpecialty.getText().toString().trim();
        String birthplace = etBirthplace.getText().toString().trim();
        String titlesStr = etTitles.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT).show();
            return;
        }

        int titles = titlesStr.isEmpty() ? 0 : Integer.parseInt(titlesStr);
        FavouriteCyclist fav = new FavouriteCyclist(name, specialty, birthplace, titles, notes);

        executor.execute(() -> {
            if (favId == -1) {
                db.favouriteCyclistDao().insert(fav);
            } else {
                fav.setId(favId);
                db.favouriteCyclistDao().update(fav);
            }
            handler.post(() -> {
                Toast.makeText(this,
                        favId == -1 ? getString(R.string.favourite_added) : getString(R.string.favourite_updated),
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}