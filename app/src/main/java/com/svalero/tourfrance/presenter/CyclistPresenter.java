package com.svalero.tourfrance.presenter;

import com.svalero.tourfrance.api.RetrofitClient;
import com.svalero.tourfrance.api.TourFranceService;
import com.svalero.tourfrance.model.Cyclist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CyclistPresenter {

    public interface CyclistView {
        void showCyclists(List<Cyclist> cyclists);
        void showError(String message);
        void showEmpty();
        void onCyclistAdded(Cyclist cyclist);
        void onCyclistUpdated(Cyclist cyclist);
        void onCyclistDeleted(long id);
    }

    private CyclistView view;
    private TourFranceService service;

    public CyclistPresenter(CyclistView view) {
        this.view = view;
        this.service = RetrofitClient.getService();
    }

    public void loadCyclists() {
        service.getAllCyclists().enqueue(new Callback<List<Cyclist>>() {
            @Override
            public void onResponse(Call<List<Cyclist>> call, Response<List<Cyclist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        view.showEmpty();
                    } else {
                        view.showCyclists(response.body());
                    }
                } else {
                    view.showError("Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Cyclist>> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }

    public void addCyclist(long teamId, Cyclist cyclist) {
        service.addCyclist(teamId, cyclist).enqueue(new Callback<Cyclist>() {
            @Override
            public void onResponse(Call<Cyclist> call, Response<Cyclist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onCyclistAdded(response.body());
                } else {
                    view.showError("Error al añadir: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Cyclist> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }

    public void updateCyclist(long id, Cyclist cyclist) {
        service.updateCyclist(id, cyclist).enqueue(new Callback<Cyclist>() {
            @Override
            public void onResponse(Call<Cyclist> call, Response<Cyclist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onCyclistUpdated(response.body());
                } else {
                    view.showError("Error al actualizar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Cyclist> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }

    public void deleteCyclist(long id) {
        service.deleteCyclist(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onCyclistDeleted(id);
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