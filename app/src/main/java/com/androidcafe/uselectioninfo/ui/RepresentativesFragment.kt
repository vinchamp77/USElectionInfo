package com.androidcafe.uselectioninfo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androidcafe.uselectioninfo.R
import com.androidcafe.uselectioninfo.data.Address
import com.androidcafe.uselectioninfo.databinding.RepresentativesFragmentBinding
import com.androidcafe.uselectioninfo.viewmodel.RepresentativesViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import java.util.*

class RepresentativesFragment : BaseFragment() {

    private val navController by lazy { findNavController() }
    override val viewModel: RepresentativesViewModel by viewModels()

    private lateinit var requestLocationPermissionLauncher : ActivityResultLauncher<String>
    private lateinit var enableLocationSettingLauncher : ActivityResultLauncher<IntentSenderRequest>

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = RepresentativesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // recycle view
        val adapter = RepresentativeListAdapter()
        binding.representativesRecyclerView.adapter = adapter
        viewModel.representatives.observe(viewLifecycleOwner, { representatives ->
            adapter.submitList(representatives)
        })

        // search button
        binding.searchButton.setOnClickListener {
            hideKeyboard()
            viewModel.onSearchButtonClick()
        }

        // use my location button
        registerLocationPermissionsCallback()
        registerEnableLocationCallback()
        binding.locationButton.setOnClickListener { requestLocationPermissions()}

        return binding.root
    }

    private fun registerLocationPermissionsCallback() {

        requestLocationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

            if (isGranted) {
                checkDeviceLocationSettingsAndGetLocation()
            } else {
                viewModel.showSnackBarInt.value = R.string.no_location_permission_msg
            }
        }
    }

    private fun registerEnableLocationCallback() {
        enableLocationSettingLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK)
                    getLocation()
                else {
                    viewModel.showSnackBarInt.value = R.string.location_required_error_msg
                }
            }
    }

    private fun requestLocationPermissions() {

        if (isLocationPermissionGranted()) {
            checkDeviceLocationSettingsAndGetLocation()
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun isLocationPermissionGranted() : Boolean {

        var granted = false
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            granted = true
        }

        return granted
    }

    /*
    *  Uses the Location Client to check the current state of location settings, and gives the user
    *  the opportunity to turn on location services within our app.
    */
    private fun checkDeviceLocationSettingsAndGetLocation(resolve:Boolean = true) {

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve){
                try {

                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    enableLocationSettingLauncher.launch(intentSenderRequest)

                } catch (sendEx: IntentSender.SendIntentException) {
                    sendEx.printStackTrace()
                }
            } else {
                viewModel.showSnackBarInt.value = R.string.location_required_error_msg
            }
        }

        locationSettingsResponseTask.addOnCompleteListener {
            if ( it.isSuccessful ) {
                getLocation()
            }
        }
    }

    //permission has been checked before calling this function
    @SuppressLint("MissingPermission")
    private fun getLocation() {

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                locationResult.let {

                    val address = geoCodeLocation(it.lastLocation)
                    if(address != null) {
                        viewModel.refreshByCurrentLocation(address)
                    }

                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }
        }

        val locationRequest = LocationRequest.create()
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        Looper.myLooper()?.let {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,
                it
            )
        }

        /* Last location is not very accurate - keeping it here for future reference */
//        val fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(requireContext())
//        val lastLocationTask = fusedLocationProviderClient.lastLocation
//
//        lastLocationTask.addOnCompleteListener(requireActivity()) { task ->
//
//            if (task.isSuccessful) {
//                val taskResult = task.result
//                taskResult?.run {
//
//                    val address = geoCodeLocation(this)
//                    viewModel.onUseMyLocation(address)
//                }
//            }
//        }

    }

    private fun geoCodeLocation(location: Location): Address? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            ?.map { address ->
                Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
            }
            ?.first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}