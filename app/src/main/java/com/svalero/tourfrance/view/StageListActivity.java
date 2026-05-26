package com.svalero.tourfrance.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.adapter.StageAdapter;
import com.svalero.tourfrance.model.Stage;
import com.svalero.tourfrance.presenter.StagePresenter;

import java.util.ArrayList;
import java.util.List;

public class StageListActivity extends AppCompatActivity
        implements StagePresenter.StageView {

    private RecyclerView recyclerView;
    private StageAdapter adapter;
    private StagePresenter presenter;
    private List<Stage> stageList = new ArrayList<>();
    private View tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_list);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_stages);
        tvEmpty = findViewById(R.id.tv_empty);

        adapter = new StageAdapter(this, stageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new StagePresenter(this);
        presenter.loadStages();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void showStages(List<Stage> stages) {
        stageList.clear();
        stageList.addAll(stages);
        adapter.setStages(stageList);
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