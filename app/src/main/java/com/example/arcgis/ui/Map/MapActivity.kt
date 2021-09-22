package com.example.arcgis.ui.Map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.MapView
import com.google.android.gms.location.*

import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import com.example.arcgis.R
import com.example.arcgis.util.Constants


class MapActivity : AppCompatActivity() {

    lateinit var mapView: MapView
    lateinit var archismap: ArcGISMap

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var isLevel2: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.mapView)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        onCLick()


    }

    private fun initMap(latitude: Double, longitude: Double, level: Int) {
        archismap = ArcGISMap(Basemap.Type.STREETS, latitude, longitude, level)

        mapView.map = archismap
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData(1)
                    } else {
                        initMap(location.latitude, location.longitude, Constants.LEVEL1)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun onCLick() {
        findViewById<Button>(R.id.zoomin).setOnClickListener {
            isLevel2 = true;
            if (checkPermissions()) {
                if (isLocationEnabled()) {
                    mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                        var location: Location? = task.result
                        if (location == null) {
                            requestNewLocationData(1)
                        } else {
                            initMap(location.latitude, location.longitude, Constants.LEVEL2)
                        }
                    }
                } else {
                    Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            } else {
                requestPermissions()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(level: Int) {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (level == 2) {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallbackLevel2,
                Looper.myLooper()
            )
        } else {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
            )
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

            initMap(mLastLocation.latitude, mLastLocation.longitude, Constants.LEVEL1)
        }
    }
    private val mLocationCallbackLevel2 = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

            initMap(mLastLocation.latitude, mLastLocation.longitude, Constants.LEVEL2)
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }


    override fun onPause() {
        mapView.pause()
        super.onPause()
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        mapView.resume()
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        if (isLevel2) {
                            requestNewLocationData(2)
                        } else {
                            requestNewLocationData(1)
                        }
                    } else {
                        if (isLevel2) {
                            initMap(location.latitude, location.longitude, Constants.LEVEL2)
                        }else{
                            initMap(location.latitude, location.longitude, Constants.LEVEL1)
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
        isLevel2 = false
    }
}

