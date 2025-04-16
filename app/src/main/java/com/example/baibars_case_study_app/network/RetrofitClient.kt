package com.example.baibars_case_study_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/")
        .addConverterFactory(GsonConverterFactory.create()).build();

    val service: TelemetryService = retrofit.create(TelemetryService::class.java);
}