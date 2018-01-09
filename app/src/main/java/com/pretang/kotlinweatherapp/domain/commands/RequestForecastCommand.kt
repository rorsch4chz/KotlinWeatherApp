package com.pretang.kotlinweatherapp.domain.commands

import com.pretang.kotlinweatherapp.domain.datasource.ForecastProvider
import com.pretang.kotlinweatherapp.domain.model.ForecastList

/**
 * @author baizhou
 * @data 2018/1/5
 * @description
 */
class RequestForecastCommand(private val zipCode: Long, private val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {
    override fun excute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }

    companion object {
        val DAYS = 7
    }
}