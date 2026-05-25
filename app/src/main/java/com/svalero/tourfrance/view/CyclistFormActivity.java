package com.svalero.tourfrance.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.model.Cyclist;
import com.svalero.tourfrance.presenter.CyclistPresenter;

import java.util.List;

public class CyclistFormActivity extends AppCompatActivity
        implements CyclistPresenter.CyclistView {

    private TextInputEditText etName, etSpecialty, etBirthplace,
            etTitles, etBirthdate, etTeamId;
    private MaterialButton btnSave;
    private CyclistPresenter presenter;

    private long cyclistId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclist_form);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etName = findViewById(R.id.et_name);
        etSpecialty = findViewById(R.id.et_specialty);
        etBirthplace = findViewById(R.id.et_birthplace);
        etTitles = findViewById(R.id.et_titles);
        etBirthdate = findViewById(R.id.et_birthdate);
        etTeamId = findViewById(R.id.et_team_id);
        btnSave = findViewById(R.id.btn_save);

        presenter = new CyclistPresenter(this);

        if (getIntent().hasExtra("cyclist_id")) {
            cyclistId = getIntent().getLongExtra("cyclist_id", -1);
            etName.setText(getIntent().getStringExtra("cyclist_name"));
            etSpecialty.setText(getIntent().getStringExtra("cyclist_specialty"));
            etBirthplace.setText(getIntent().getStringExtra("cyclist_birthplace"));
            etTitles.setText(String.valueOf(getIntent().getIntExtra("cyclist_titles", 0)));
            etBirthdate.setText(getIntent().getStringExtra("cyclist_birthdate"));
            etTeamId.setText(String.valueOf(getIntent().getLongExtra("cyclist_team_id", 0)));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Editar ciclista");
            }
        }

        btnSave.setOnClickListener(v -> saveCyclist());
    }

    private void saveCyclist() {
        String name = etName.getText().toString().trim();
        String specialty = etSpecialty.getText().toString().trim();
        String birthplace = etBirthplace.getText().toString().trim();
        String titlesStr = etTitles.getText().toString().trim();
        String birthdate = etBirthdate.getText().toString().trim();
        String teamIdStr = etTeamId.getText().toString().trim();

        if (name.isEmpty() || specialty.isEmpty() || birthdate.isEmpty()) {
            Toast.makeText(this, "Nombre, especialidad y fecha son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int titles = titlesStr.isEmpty() ? 0 : Integer.parseInt(titlesStr);
        long teamId = teamIdStr.isEmpty() ? 0 : Long.parseLong(teamIdStr);

        Cyclist cyclist = new Cyclist(name, specialty, birthplace, titles, birthdate, teamId);

        if (cyclistId == -1) {
            presenter.addCyclist(teamId, cyclist);
        } else {
            presenter.updateCyclist(cyclistId, cyclist);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void showCyclists(List<Cyclist> cyclists) {}

    @Override
    public void showEmpty() {}

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCyclistAdded(Cyclist cyclist) {
        Toast.makeText(this, "Ciclista añadido correctamente", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onCyclistUpdated(Cyclist cyclist) {
        Toast.makeText(this, "Ciclista actualizado correctamente", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onCyclistDeleted(long id) {}
}