package com.example.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.helper.Constants
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (MySharedPreferences.getUnits() == Constants.CELSIUS) rbCelsius.isChecked = true
        else rbFahrenheit.isChecked = true

        rgTemperature.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(rg: RadioGroup?, id: Int) {
                if (id == R.id.rbCelsius)
                    MySharedPreferences.setUnits(Constants.CELSIUS)
                else
                    MySharedPreferences.setUnits(Constants.FAHRENHEIT)
            }
        })

        etName.setText(MySharedPreferences.getUserName())

    }
}