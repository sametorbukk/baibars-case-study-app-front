package com.example.baibars_case_study_app.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.baibars_case_study_app.databinding.FragmentDashboardBinding
import com.example.baibars_case_study_app.network.RetrofitClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDashboardBinding? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private var currentLocation: LatLng = LatLng(41.015137, 28.979530)


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        startFetchingTelemetry()
        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
        mMap.addMarker(MarkerOptions().position(currentLocation).title("Başlangıç Noktası"))
    }

    private fun startFetchingTelemetry() {
        scope.launch {
            while (true) {
                try {
                    val data = withContext(Dispatchers.IO) {
                        RetrofitClient.service.getTelemetryData()
                    }

                    _binding?.let { binding ->
                        binding.voltageText.text = "Voltage: ${data.voltage}V"
                        binding.gpsText.text = "GPS: ${data.gpsCoordinates}"
                        binding.altitudeText.text = "Altitude: ${data.altitude}m"
                        binding.flightTimeText.text = "Flight Time: ${data.flightTime}"

                        // Update map location
                        val coordinates = data.gpsCoordinates.split(",")
                        if (coordinates.size == 2) {
                            val lat = coordinates[0].trim().toDoubleOrNull()
                            val lng = coordinates[1].trim().toDoubleOrNull()
                            if (lat != null && lng != null) {
                                val newLocation = LatLng(lat, lng)
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation))
                                mMap.clear()
                                mMap.addMarker(MarkerOptions().position(newLocation).title("Current Location"))
                            }
                        }
                    }

                    Log.d("TelemetryData", "Gelen veri: $data")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                delay(3000)
            }
        }
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
        scope.coroutineContext.cancel()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}