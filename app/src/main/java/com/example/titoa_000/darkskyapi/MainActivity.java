package com.example.titoa_000.darkskyapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.titoa_000.darkskyapi.services.WeatherService;

import models.Currently;
import models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/fad9c36df0aecb9825ca6d1b20f6fcb8/37.8267,-122.4233/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<Weather> weatherCall = weatherService.getWeather();
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Currently currently = response.body().getCurrently();
                Log.e(TAG, "Temperature = " + currently.getTemperature());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to get weather data");
            }
        });
    }
}

