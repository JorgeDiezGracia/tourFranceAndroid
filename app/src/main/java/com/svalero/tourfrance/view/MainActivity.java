package com.svalero.tourfrance.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.svalero.tourfrance.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Aplicar tema antes de inflar la vista
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", "light");
        switch (theme) {
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MaterialCardView cardCyclists = findViewById(R.id.card_cyclists);
        cardCyclists.setOnClickListener(v ->
                startActivity(new Intent(this, CyclistListActivity.class)));

        MaterialCardView cardTeams = findViewById(R.id.card_teams);
        cardTeams.setOnClickListener(v ->
                startActivity(new Intent(this, TeamListActivity.class)));

        MaterialCardView cardStages = findViewById(R.id.card_stages);
        cardStages.setOnClickListener(v ->
                startActivity(new Intent(this, StageListActivity.class)));

        MaterialCardView cardClimbs = findViewById(R.id.card_climbs);
        cardClimbs.setOnClickListener(v ->
                startActivity(new Intent(this, ClimbListActivity.class)));

        MaterialCardView cardFavourites = findViewById(R.id.card_favourites);
        cardFavourites.setOnClickListener(v ->
                startActivity(new Intent(this, FavouriteListActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}