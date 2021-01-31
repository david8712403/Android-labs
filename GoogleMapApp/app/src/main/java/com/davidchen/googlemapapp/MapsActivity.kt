package com.davidchen.googlemapapp

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val REQUEST_PERMISSIONS = 1
    private lateinit var mMap: GoogleMap

    private val locTaipei101 = LatLng(25.033611, 121.565000)
    private val locTaipeiStation = LatLng(25.047924, 121.517081)
    private val locHome = LatLng(24.996503, 121.522302)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS)
        } else {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) {
            return
        }
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        finish()
                    }else {
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(map: GoogleMap) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        mMap = map
        mMap.isMyLocationEnabled = true

        val marker = MarkerOptions()
        marker.apply {
            position(locTaipei101)
            title("Taipei 101")
            draggable(true)
        }
        map.addMarker(marker)

        marker.apply {
            position(locHome)
            title("home")
            draggable(true)
        }
        map.addMarker(marker)

        marker.apply {
            position(locTaipeiStation)
            title("Taipei station")
            draggable(true)
        }
        map.addMarker(marker)

        val polylineOpt = PolylineOptions()
        polylineOpt.add(locTaipei101)
        polylineOpt.add(locHome)
        polylineOpt.add(locTaipeiStation)
        polylineOpt.add(locTaipei101)
        polylineOpt.color(Color.BLUE)

        val polyline = map.addPolyline(polylineOpt)
        polyline.width = 10f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.034, 121.545), 13f))
    }
}