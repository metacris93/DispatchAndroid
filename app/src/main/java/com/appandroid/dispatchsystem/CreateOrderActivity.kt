package com.appandroid.dispatchsystem

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.appandroid.dispatchsystem.Model.ApiResponse
import com.appandroid.dispatchsystem.Model.IApi
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrderActivity : AppCompatActivity() {
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var latitude: String
    lateinit var longitude: String
    var storeId : Int = 0
    /*https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html*/

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)

        intent?.extras?.let {
            storeId = it.getInt("store_id")
        }

        supportActionBar!!.title = "Create New Order"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // finding the button
        val showButton = findViewById<Button>(R.id.btnCreateOrder)

        // finding the edit text
        val descriptionOrder = findViewById<EditText>(R.id.orderName)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        showButton.setOnClickListener {
            val description = descriptionOrder.text

            val json = JSONObject()
            json.put("lat", latitude.toFloatOrNull())
            json.put("lng", longitude.toFloatOrNull())
            json.put("store_id", storeId)
            json.put("description", description)
            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
            val call: Call<ApiResponse> = IApi.create().createOrder(requestBody)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("ERROR", t.message.toString())
                }

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.code() == 200)
                    {
                        Toast.makeText(applicationContext, "Orden creada", Toast.LENGTH_SHORT).show()
                        finish()
                        /*val intent = Intent(this@CreateOrderActivity, OrdersActivity::class.java)
                        startActivity(intent)*/
                    }
                }
            })
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitude = location.latitude.toString()
                        longitude = location.longitude.toString()
                        Log.e("COORDENADAS", "lat: ${latitude} | lng: ${longitude}");
                    }
                }
            } else {
                Toast.makeText(this, "Activar la ubicacion GPS", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

            latitude = mLastLocation.latitude.toString()
            longitude = mLastLocation.longitude.toString()
            Log.e("COORDENADAS", "lat: ${latitude} | lng: ${longitude}");
        }
    }
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}
