package com.svalero.tourfrance.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.model.Team;
import com.svalero.tourfrance.presenter.TeamPresenter;

import java.util.List;

public class TeamFormActivity extends AppCompatActivity
        implements TeamPresenter.TeamView {

    private TextInputEditText etName, etCountry, etEmail, etBudget, etFundationDate;
    private TeamPresenter presenter;
    private long teamId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_form);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etName = findViewById(R.id.et_name);
        etCountry = findViewById(R.id.et_country);
        etEmail = findViewById(R.id.et_email);
        etBudget = findViewById(R.id.et_budget);
        etFundationDate = findViewById(R.id.et_fundation_date);
        MaterialButton btnSave = findViewById(R.id.btn_save);

        presenter = new TeamPresenter(this);

        if (getIntent().hasExtra("team_id")) {
            teamId = getIntent().getLongExtra("team_id", -1);
            etName.setText(getIntent().getStringExtra("team_name"));
            etCountry.setText(getIntent().getStringExtra("team_country"));
            etEmail.setText(getIntent().getStringExtra("team_email"));
            etBudget.setText(String.valueOf(getIntent().getFloatExtra("team_budget", 0)));
            etFundationDate.setText(getIntent().getStringExtra("team_fundation_date"));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.edit_team_title);
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.add_team_title);
            }
        }

        btnSave.setOnClickListener(v -> saveTeam());
    }

    private void saveTeam() {
        String name = etName.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String budgetStr = etBudget.getText().toString().trim();
        String fundationDate = etFundationDate.getText().toString().trim();

        if (name.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, R.string.country_required, Toast.LENGTH_SHORT).show();
            return;
        }

        float budget = budgetStr.isEmpty() ? 0 : Float.parseFloat(budgetStr);
        Team team = new Team(name, country, email, budget, fundationDate);

        if (teamId == -1) {
            presenter.addTeam(team);
        } else {
            presenter.updateTeam(teamId, team);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override public void showTeams(List<Team> teams) {}
    @Override public void showEmpty() {}

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTeamAdded(Team team) {
        Toast.makeText(this, R.string.team_added, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onTeamUpdated(Team team) {
        Toast.makeText(this, R.string.team_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override public void onTeamDeleted(long id) {}
}