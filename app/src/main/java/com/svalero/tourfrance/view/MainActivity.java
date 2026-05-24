package com.svalero.tourfrance.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.svalero.tourfrance.R;
import com.svalero.tourfrance.api.RetrofitClient;
import com.svalero.tourfrance.api.TourFranceService;
import com.svalero.tourfrance.model.Cyclist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TourFrance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testApiConnection();
    }

    private void testApiConnection() {
        TourFranceService service = RetrofitClient.getService();

        service.getAllCyclists().enqueue(new Callback<List<Cyclist>>() {
            @Override
            public void onResponse(Call<List<Cyclist>> call, Response<List<Cyclist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int count = response.body().size();
                    Log.d(TAG, "Conexión OK - ciclistas recibidos: " + count);
                    Toast.makeText(MainActivity.this,
                            "✅ API conectada - " + count + " ciclistas", Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "Error HTTP: " + response.code());
                    Toast.makeText(MainActivity.this,
                            "❌ Error HTTP: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cyclist>> call, Throwable t) {
                Log.e(TAG, "Error conexión: " + t.getMessage());
                Toast.makeText(MainActivity.this,
                        "❌ Sin conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}