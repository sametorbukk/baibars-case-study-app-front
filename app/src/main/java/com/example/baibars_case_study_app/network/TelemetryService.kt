package com.example.baibars_case_study_app.network

import com.example.baibars_case_study_app.entity.TelemetryDataDto
import retrofit2.http.GET

interface TelemetryService {

    @GET("telemetry")
    suspend fun getTelemetryData(): TelemetryDataDto;
}