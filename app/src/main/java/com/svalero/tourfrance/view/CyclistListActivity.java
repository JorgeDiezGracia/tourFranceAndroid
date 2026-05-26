package com.svalero.tourfrance.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.adapter.CyclistAdapter;
import com.svalero.tourfrance.model.Cyclist;
import com.svalero.tourfrance.presenter.CyclistPresenter;

import java.util.ArrayList;
import java.util.List;

public class CyclistListActivity extends AppCompatActivity
        implements CyclistPresenter.CyclistView, CyclistAdapter.OnCyclistClickListener {

    private RecyclerView recyclerView;
    private CyclistAdapter adapter;
    private CyclistPresenter presenter;
    private List<Cyclist> cyclistList = new ArrayList<>();
    private View tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_list);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_cyclists);
        tvEmpty = findViewById(R.id.tv_empty);
        FloatingActionButton fab = findViewById(R.id.fab_add_cyclist);

        adapter = new CyclistAdapter(this, cyclistList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new CyclistPresenter(this);
        presenter.loadCyclists();

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, CyclistFormActivity.class);
            startActivityForResult(intent, 1);
        });
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
            presenter.loadCyclists();
        }
    }

    @Override
    public void showCyclists(List<Cyclist> cyclists) {
        cyclistList.clear();
        cyclistList.addAll(cyclists);
        adapter.setCyclists(cyclistList);
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

    @Override
    public void onCyclistAdded(Cyclist cyclist) {
        cyclistList.add(cyclist);
        adapter.notifyItemInserted(cyclistList.size() - 1);
    }

    @Override
    public void onCyclistUpdated(Cyclist cyclist) {
        for (int i = 0; i < cyclistList.size(); i++) {
            if (cyclistList.get(i).getId() == cyclist.getId()) {
                cyclistList.set(i, cyclist);
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onCyclistDeleted(long id) {
        for (int i = 0; i < cyclistList.size(); i++) {
            if (cyclistList.get(i).getId() == id) {
                cyclistList.remove(i);
                adapter.notifyItemRemoved(i);
                break;
            }
        }
        if (cyclistList.isEmpty()) showEmpty();
    }

    @Override
    public void onCyclistClick(Cyclist cyclist) {
        Intent intent = new Intent(this, CyclistFormActivity.class);
        intent.putExtra("cyclist_id", cyclist.getId());
        intent.putExtra("cyclist_name", cyclist.getName());
        intent.putExtra("cyclist_specialty", cyclist.getSpecialty());
        intent.putExtra("cyclist_birthplace", cyclist.getBirthplace());
        intent.putExtra("cyclist_titles", cyclist.getTitles());
        intent.putExtra("cyclist_birthdate", cyclist.getBirthdate());
        intent.putExtra("cyclist_team_id", cyclist.getTeamId());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onCyclistLongClick(Cyclist cyclist) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_cyclist)
                .setMessage(getString(R.string.confirm_delete) + " " + cyclist.getName() + "?")
                .setPositiveButton(R.string.yes, (dialog, which) ->
                        presenter.deleteCyclist(cyclist.getId()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}