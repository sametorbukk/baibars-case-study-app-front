package com.example.baibars_case_study_app.entity

data class TelemetryDataDto(
    val voltage: Double,
    val altitude: Int,
    val gpsCoordinates: String,
    val flightTime: String
)