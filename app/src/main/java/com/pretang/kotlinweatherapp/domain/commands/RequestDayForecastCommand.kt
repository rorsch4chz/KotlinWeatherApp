package com.pretang.kotlinweatherapp.domain.commands

import com.pretang.kotlinweatherapp.domain.datasource.ForecastProvider
import com.pretang.kotlinweatherapp.domain.model.Forecast

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
class RequestDayForecastCommand(val id: Long,
                                val forecastProvider: ForecastProvider = ForecastProvider()) : Command<Forecast> {
    override fun excute() = forecastProvider.requestForecast(id)
}