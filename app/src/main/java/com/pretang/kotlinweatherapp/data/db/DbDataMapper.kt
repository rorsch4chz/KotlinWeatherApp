package com.pretang.kotlinweatherapp.data.db

import com.pretang.kotlinweatherapp.domain.model.Forecast
import com.pretang.kotlinweatherapp.domain.model.ForecastList

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
class DbDataMapper {
    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(date, date, description, high, low, iconUrl)
    }
}