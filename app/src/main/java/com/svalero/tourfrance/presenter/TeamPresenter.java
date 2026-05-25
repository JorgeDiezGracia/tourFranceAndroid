package com.svalero.tourfrance.presenter;

import com.svalero.tourfrance.api.RetrofitClient;
import com.svalero.tourfrance.api.TourFranceService;
import com.svalero.tourfrance.model.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPresenter {

    public interface TeamView {
        void showTeams(List<Team> teams);
        void showError(String message);
        void showEmpty();
        void onTeamAdded(Team team);
        void onTeamUpdated(Team team);
        void onTeamDeleted(long id);
    }

    private TeamView view;
    private TourFranceService service;

    public TeamPresenter(TeamView view) {
        this.view = view;
        this.service = RetrofitClient.getService();
    }

    public void loadTeams() {
        service.getAllTeams().enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        view.showEmpty();
                    } else {
                        view.showTeams(response.body());
                    }
                } else {
                    view.showError("Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }

    public void addTeam(Team team) {
        service.addTeam(team).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onTeamAdded(response.body());
                } else {
                    view.showError("Error al añadir: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }

    public void updateTeam(long id, Team team) {
        service.updateTeam(id, team).enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onTeamUpdated(response.body());
                } else {
                    view.showError("Error al actualizar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }

    public void deleteTeam(long id) {
        service.deleteTeam(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onTeamDeleted(id);
                } else {
                    view.showError("Error al eliminar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }
}
