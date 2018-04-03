package com.example.titoa_000.darkskyapi.services;

import android.util.Log;

import com.example.titoa_000.darkskyapi.events.WeatherEvent;

import org.greenrobot.eventbus.EventBus;

import models.Currently;
import models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherServiceProvider {
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();
    private Retrofit retrofit;
    private static String BASE_URL = "https://api.darksky.net/forecast/fad9c36df0aecb9825ca6d1b20f6fcb8/";

    private Retrofit getRetrofit(){
        if(this.retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;
    }

    public void getWeather(double lat, double lng){
        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        Call<Weather> weatherCall = weatherService.getWeather(lat, lng);
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather =  response.body();
                Currently currently = weather.getCurrently();
                Log.e(TAG, "Temperature =  " + currently.getTemperature());
                EventBus.getDefault().post(new WeatherEvent(weather));
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to get weather data");
            }
        });
    }
}
