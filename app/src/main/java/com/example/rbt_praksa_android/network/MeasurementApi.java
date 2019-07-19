package com.example.rbt_praksa_android.network;

import com.example.rbt_praksa_android.model.AirVironment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MeasurementApi {

    @GET("measurement/latest")
    Call<AirVironment> getMeasurementData();
}