package com.svalero.tourfrance.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.common.MapboxOptions;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.tourfrance.R;
import com.svalero.tourfrance.api.RetrofitClient;
import com.svalero.tourfrance.api.TourFranceService;
import com.svalero.tourfrance.model.Climb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapboxOptions.setAccessToken(
                "pk.eyJ1Ijoiam9yZ2V0ZW4xMCIsImEiOiJjbTZnYnQyNzgwY3EzMmpzMGZ2M21xaXQyIn0.alTlyO8vRKgDIhEVWEzdBg"
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            loadClimbs();
        });
    }

    private void loadClimbs() {
        TourFranceService service = RetrofitClient.getService();
        service.getAllClimbs().enqueue(new Callback<List<Climb>>() {
            @Override
            public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addMarkers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Climb>> call, Throwable t) {}
        });
    }

    private void addMarkers(List<Climb> climbs) {
        for (Climb climb : climbs) {
            if (climb.getLatitude() != 0 && climb.getLongitude() != 0) {
                mapView.getMapboxMap().setCamera(
                        new CameraOptions.Builder()
                                .center(Point.fromLngLat(climb.getLongitude(), climb.getLatitude()))
                                .zoom(8.0)
                                .build()
                );
            }
        }
    }
}