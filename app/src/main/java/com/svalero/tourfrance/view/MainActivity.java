package com.svalero.tourfrance.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.svalero.tourfrance.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView cardCyclists = findViewById(R.id.card_cyclists);
        cardCyclists.setOnClickListener(v -> {
            startActivity(new Intent(this, CyclistListActivity.class));
        });

        MaterialCardView cardTeams = findViewById(R.id.card_teams);
        cardTeams.setOnClickListener(v -> {
            startActivity(new Intent(this, TeamListActivity.class));
        });

        MaterialCardView cardStages = findViewById(R.id.card_stages);
        cardStages.setOnClickListener(v -> {
            startActivity(new Intent(this, StageListActivity.class));
        });

        MaterialCardView cardClimbs = findViewById(R.id.card_climbs);
        cardClimbs.setOnClickListener(v -> {
            startActivity(new Intent(this, ClimbListActivity.class));
        });
    }
}