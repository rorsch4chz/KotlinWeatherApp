package com.pretang.kotlinweatherapp.domain.model

/**
 * @author baizhou
 * @data 2018/1/5
 * @description
 */
data class ForecastList(val id: Long, val city: String, val country: String, val dailyForecast: List<Forecast>)
data class Forecast(val id: Long, val date: Long, val description: String, val high: Int, val low: Int, val iconUrl: String)