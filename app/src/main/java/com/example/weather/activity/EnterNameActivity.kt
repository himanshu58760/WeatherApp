package com.example.weather.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import kotlinx.android.synthetic.main.activity_enter_name.*

class EnterNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        btnSubmit.setOnClickListener {
            if (etName.text.toString().isNotBlank()) {
                MySharedPreferences.setUserName(etName.text.toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.name_cant_be_empty), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}