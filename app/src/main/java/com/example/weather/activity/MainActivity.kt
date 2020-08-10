package com.example.weather.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.fragments.CurrentWeatherFragment
import com.example.weather.fragments.DateWeatherFragment
import com.example.weather.fragments.ForecastFragment
import com.example.weather.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentWeatherFragment = CurrentWeatherFragment()
    private val dateWeatherFragment = DateWeatherFragment()
    private val forecastFragment = ForecastFragment()
    private val settingsFragment = SettingsFragment()
    private var activeFragment: Fragment = currentWeatherFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addAllFragments()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.current -> setFragment(currentWeatherFragment.javaClass.simpleName)
                R.id.date -> setFragment(dateWeatherFragment.javaClass.simpleName)
                R.id.forecast -> setFragment(forecastFragment.javaClass.simpleName)
                R.id.settings -> setFragment(settingsFragment.javaClass.simpleName)
                else -> true
            }
        }
    }

    private fun setFragment(tag: String): Boolean {
        when (tag) {
            CurrentWeatherFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(currentWeatherFragment)
                    .commit()
                activeFragment = currentWeatherFragment
            }
            DateWeatherFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(dateWeatherFragment)
                    .commit()
                activeFragment = dateWeatherFragment
            }
            ForecastFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(forecastFragment)
                    .commit()
                activeFragment = forecastFragment
            }
            SettingsFragment::class.java.simpleName -> {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(settingsFragment)
                    .commit()
                activeFragment = settingsFragment
            }
        }
        return true
    }

    private fun addAllFragments() {

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout, dateWeatherFragment,
            dateWeatherFragment.javaClass.simpleName
        ).hide(dateWeatherFragment).commit()

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout, forecastFragment,
            forecastFragment.javaClass.simpleName
        ).hide(forecastFragment).commit()

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout, settingsFragment,
            settingsFragment.javaClass.simpleName
        ).hide(settingsFragment).commit()

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout, currentWeatherFragment,
            currentWeatherFragment.javaClass.simpleName
        ).commit()
    }
}