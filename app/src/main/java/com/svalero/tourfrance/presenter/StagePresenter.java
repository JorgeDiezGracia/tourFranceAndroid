package com.svalero.tourfrance.presenter;

import com.svalero.tourfrance.api.RetrofitClient;
import com.svalero.tourfrance.api.TourFranceService;
import com.svalero.tourfrance.model.Stage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StagePresenter {

    public interface StageView {
        void showStages(List<Stage> stages);
        void showError(String message);
        void showEmpty();
    }

    private StageView view;
    private TourFranceService service;

    public StagePresenter(StageView view) {
        this.view = view;
        this.service = RetrofitClient.getService();
    }

    public void loadStages() {
        service.getAllStages().enqueue(new Callback<List<Stage>>() {
            @Override
            public void onResponse(Call<List<Stage>> call, Response<List<Stage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        view.showEmpty();
                    } else {
                        view.showStages(response.body());
                    }
                } else {
                    view.showError("Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Stage>> call, Throwable t) {
                view.showError("Sin conexión: " + t.getMessage());
            }
        });
    }
}