package com.example.weather.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.helper.LocationHelper

class SplashActivity : AppCompatActivity() {

    val handler by lazy { Handler(mainLooper) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!LocationHelper.isLocationPermissionGranted(this))
            LocationHelper.requestLocationPermission(this)
        else
            handler.postDelayed(this::openNextActivity, 2500)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationHelper.MY_PERMISSIONS_REQUEST_FINE_LOCATION &&
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handler.postDelayed(this::openNextActivity, 2500)
            return
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.title_location_permission)
                .setMessage(R.string.text_location_permission)
                .setPositiveButton(R.string.ok) { alert, _ ->
                    handler.postDelayed(this::openNextActivity, 2500)
                }.create().show()
        }
    }

    fun openNextActivity() {
        if (MySharedPreferences.getUserName().isBlank())
            startActivity(Intent(this, EnterNameActivity::class.java))
        else
            startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}