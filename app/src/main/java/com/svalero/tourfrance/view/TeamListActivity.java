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
import com.svalero.tourfrance.adapter.TeamAdapter;
import com.svalero.tourfrance.model.Team;
import com.svalero.tourfrance.presenter.TeamPresenter;

import java.util.ArrayList;
import java.util.List;

public class TeamListActivity extends AppCompatActivity
        implements TeamPresenter.TeamView, TeamAdapter.OnTeamClickListener {

    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private TeamPresenter presenter;
    private List<Team> teamList = new ArrayList<>();
    private View tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.rv_teams);
        tvEmpty = findViewById(R.id.tv_empty);
        FloatingActionButton fab = findViewById(R.id.fab_add_team);

        adapter = new TeamAdapter(this, teamList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new TeamPresenter(this);
        presenter.loadTeams();

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeamFormActivity.class);
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
            presenter.loadTeams();
        }
    }

    @Override
    public void showTeams(List<Team> teams) {
        teamList.clear();
        teamList.addAll(teams);
        adapter.setTeams(teamList);
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
    public void onTeamAdded(Team team) {
        teamList.add(team);
        adapter.notifyItemInserted(teamList.size() - 1);
    }

    @Override
    public void onTeamUpdated(Team team) {
        for (int i = 0; i < teamList.size(); i++) {
            if (teamList.get(i).getId() == team.getId()) {
                teamList.set(i, team);
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onTeamDeleted(long id) {
        for (int i = 0; i < teamList.size(); i++) {
            if (teamList.get(i).getId() == id) {
                teamList.remove(i);
                adapter.notifyItemRemoved(i);
                break;
            }
        }
        if (teamList.isEmpty()) showEmpty();
    }

    @Override
    public void onTeamClick(Team team) {
        Intent intent = new Intent(this, TeamFormActivity.class);
        intent.putExtra("team_id", team.getId());
        intent.putExtra("team_name", team.getName());
        intent.putExtra("team_country", team.getCountry());
        intent.putExtra("team_email", team.getEmail());
        intent.putExtra("team_budget", team.getBudget());
        intent.putExtra("team_fundation_date", team.getFundationDate());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onTeamLongClick(Team team) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_team)
                .setMessage(getString(R.string.confirm_delete) + " " + team.getName() + "?")
                .setPositiveButton(R.string.yes, (dialog, which) ->
                        presenter.deleteTeam(team.getId()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}