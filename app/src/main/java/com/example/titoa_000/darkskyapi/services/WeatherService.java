package com.example.titoa_000.darkskyapi.services;

import models.Weather;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherService {
    @GET(".")
    Call<Weather> getWeather();
}
