package com.example.baibars_case_study_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private var currentLocation: LatLng = LatLng(41.015137, 28.979530)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))


        mMap.addMarker(MarkerOptions().position(currentLocation).title("Başlangıç Noktası"))


        startLocationUpdates()
    }

    private fun startLocationUpdates() {

        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {

                val newLocation = LatLng(
                    41.015137 + Math.random() * 0.01,
                    28.979530 + Math.random() * 0.01
                )


                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation))


                mMap.clear()
                mMap.addMarker(MarkerOptions().position(newLocation).title("Yeni Konum"))


                handler.postDelayed(this, 3000)
            }
        }

        handler.post(runnable)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
