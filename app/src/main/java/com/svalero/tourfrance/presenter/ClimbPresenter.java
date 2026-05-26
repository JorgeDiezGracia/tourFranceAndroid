package com.svalero.tourfrance.presenter;

import com.svalero.tourfrance.api.RetrofitClient;
import com.svalero.tourfrance.api.TourFranceService;
import com.svalero.tourfrance.model.Climb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClimbPresenter {

    public interface ClimbView {
        void showClimbs(List<Climb> climbs);
        void showError(String message);
        void showEmpty();
    }

    private ClimbView view;
    private TourFranceService service;

    public ClimbPresenter(ClimbView view) {
        this.view = view;
        this.service = RetrofitClient.getService();
    }

    public void loadClimbs() {
        service.getAllClimbs().enqueue(new Callback<List<Climb>>() {
            @Override
            public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        view.showEmpty();
                    } else {
                        view.showClimbs(response.body());
                    }
                } else {
                    view.showError("Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Climb>> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }
}
