package org.chk.peeper.activity;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.chk.peeper.R;
import org.chk.peeper.adapter.WeatherStationDataAdapter;
import org.chk.peeper.model.WeatherStationData;
import org.chk.peeper.model.WeatherStationDataList;
import org.chk.peeper.rest.WhetherStationApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;

public class WeatherStationActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://wiski.tirol.gv.at/lawine/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_station);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectAndGetApiData();
    }


    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

        }

        WhetherStationApiService service = retrofit.create(WhetherStationApiService.class);
        Call<WeatherStationDataList> call = service.getData();
        call.enqueue(new Callback<WeatherStationDataList>() {
            @Override
            public void onResponse(Call<WeatherStationDataList> call, Response<WeatherStationDataList> response) {

                List<WeatherStationData> data = response.body().getFeatures();
                Log.d("weatherstation","nr of stations: " + data.size());

                recyclerView.setAdapter(new WeatherStationDataAdapter(data, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<WeatherStationDataList> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }
}