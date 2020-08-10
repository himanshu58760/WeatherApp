package com.example.weather.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.adapter.DailyAdapter
import com.example.weather.helper.Constants
import com.example.weather.models.Daily
import com.example.weather.models.ForecastWeatherResponse
import com.example.weather.network.Status
import com.example.weather.viewmodel.ForecastWeatherViewModel
import kotlinx.android.synthetic.main.current_fragment.tvUserName
import kotlinx.android.synthetic.main.forecast_fragment.*
import kotlinx.android.synthetic.main.forecast_fragment.swipe_refresh

class ForecastFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val viewModel by viewModels<ForecastWeatherViewModel>()
    private val dailyList = mutableListOf<Daily>()
    private val dailyAdapter = DailyAdapter(dailyList)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.forecast_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUserName.text = getString(R.string.hi_user_name, MySharedPreferences.getUserName())
        rvForecast.adapter = dailyAdapter
        swipe_refresh.setOnRefreshListener { viewModel.fetchForecastWeather() }
        viewModel.fetchForecastWeather()
        initObservers()
        context?.getSharedPreferences(
            Constants.DEFAULT_SHARED_PREFS,
            Constants.DEFAAULT_MODE_SHARED_PREFS
        )?.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initObservers() {
        viewModel.statusLiveData.observe(viewLifecycleOwner) {
            swipe_refresh.isRefreshing = !(it.status == Status.SUCCESS || it.status == Status.ERROR)
            if (it.status == Status.ERROR)
                Toast.makeText(
                    context,
                    it.message ?: getString(R.string.some_error_occoured),
                    Toast.LENGTH_SHORT
                ).show()
        }

        viewModel.forecastWeatherLiveData.observe(viewLifecycleOwner) {
            updateUi(it)
        }
    }

    private fun updateUi(forcastWeatherResponse: ForecastWeatherResponse) {
        group.visibility = View.VISIBLE
        dailyList.clear()
        dailyList.addAll(forcastWeatherResponse.daily)
        dailyAdapter.notifyDataSetChanged()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        viewModel.fetchForecastWeather()
    }

}