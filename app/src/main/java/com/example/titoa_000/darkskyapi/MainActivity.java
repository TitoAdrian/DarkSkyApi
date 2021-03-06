package com.example.titoa_000.darkskyapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.titoa_000.darkskyapi.events.ErrorEvent;
import com.example.titoa_000.darkskyapi.events.WeatherEvent;
import com.example.titoa_000.darkskyapi.services.WeatherServiceProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Currently;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tempTextView)
    TextView tempTextView;
    @BindView(R.id.iconImageView)
    ImageView iconImageView;
    @BindView(R.id.sumaryTextView)
    TextView sumaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestCurrentWeather(37.8267, -122.4233);

        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent weatherEvent){
        Currently currently = weatherEvent.getWeather().getCurrently();
        tempTextView.setText(String.valueOf(Math.round(currently.getTemperature())));
        sumaryTextView.setText(currently.getSummary());
        iconImageView.setImageResource(R.drawable.ic_clear_day);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent errorEvent){
        Toast.makeText(this, errorEvent.getErrorMessage(), Toast.LENGTH_SHORT);
    }

    private void requestCurrentWeather(double lat, double lng){
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();
        weatherServiceProvider.getWeather(lat, lng);
    }

}

