package com.svalero.tourfrance.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.adapter.ClimbAdapter;
import com.svalero.tourfrance.model.Climb;
import com.svalero.tourfrance.presenter.ClimbPresenter;

import java.util.ArrayList;
import java.util.List;

public class ClimbListActivity extends AppCompatActivity
        implements ClimbPresenter.ClimbView {

    private RecyclerView recyclerView;
    private ClimbAdapter adapter;
    private ClimbPresenter presenter;
    private List<Climb> climbList = new ArrayList<>();
    private View tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb_list);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_climbs);
        tvEmpty = findViewById(R.id.tv_empty);

        adapter = new ClimbAdapter(this, climbList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new ClimbPresenter(this);
        presenter.loadClimbs();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void showClimbs(List<Climb> climbs) {
        climbList.clear();
        climbList.addAll(climbs);
        adapter.setClimbs(climbList);
        recyclerView.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }
}