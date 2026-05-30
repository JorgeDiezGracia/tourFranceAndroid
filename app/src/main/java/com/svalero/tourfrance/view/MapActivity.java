package com.svalero.tourfrance.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.common.MapboxOptions;
import com.mapbox.geojson.Point;
import com.mapbox.maps.AnnotatedFeature;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.ViewAnnotationAnchor;
import com.mapbox.maps.ViewAnnotationOptions;
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
        MapboxOptions.setAccessToken(getString(R.string.mapbox_access_token));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> loadClimbs());
    }

    private void loadClimbs() {
        TourFranceService service = RetrofitClient.getService();
        service.getAllClimbs().enqueue(new Callback<List<Climb>>() {
            @Override
            public void onResponse(Call<List<Climb>> call, Response<List<Climb>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    runOnUiThread(() -> addMarkers(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Climb>> call, Throwable t) {}
        });
    }

    private void addMarkers(List<Climb> climbs) {
        for (Climb climb : climbs) {
            if (climb.getLatitude() != 0 && climb.getLongitude() != 0) {
                Point point = Point.fromLngLat(climb.getLongitude(), climb.getLatitude());

                mapView.getMapboxMap().setCamera(
                        new CameraOptions.Builder()
                                .center(point)
                                .zoom(10.0)
                                .build()
                );

                android.widget.ImageView markerView = new android.widget.ImageView(this);
                markerView.setImageResource(android.R.drawable.ic_menu_mylocation);
                markerView.setColorFilter(Color.RED);
                markerView.setLayoutParams(new android.view.ViewGroup.LayoutParams(80, 80));

               // TextView markerView = new TextView(this);
                //markerView.setText("⛰ " + climb.getName());
                //markerView.setBackgroundColor(Color.WHITE);
                //markerView.setTextColor(Color.BLACK);
                //markerView.setPadding(16, 8, 16, 8);
                //markerView.setGravity(Gravity.CENTER);
                //markerView.setTextSize(12);
                // Añadir esto:
                markerView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                ViewAnnotationOptions options = new ViewAnnotationOptions.Builder()
                        .annotatedFeature(new AnnotatedFeature(point))
                        .allowOverlap(true)
                        .build();

                try {
                    mapView.getViewAnnotationManager().addViewAnnotation(markerView, options);
                    android.util.Log.d("MapActivity", "Added marker for: " + climb.getName());
                } catch (Exception e) {
                    android.util.Log.e("MapActivity", "Error adding marker: " + e.getMessage());
                }
            }
        }
    }
}