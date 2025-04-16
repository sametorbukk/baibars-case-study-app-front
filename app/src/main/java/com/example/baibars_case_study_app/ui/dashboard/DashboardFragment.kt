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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        startFetchingTelemetry();
        return root
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
                    }

                    Log.d("TelemetryData", "Gelen veri: $data")


                } catch (e: Exception) {
                    e.printStackTrace()
                }

                delay(3000)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.coroutineContext.cancel()
        _binding = null
    }
}