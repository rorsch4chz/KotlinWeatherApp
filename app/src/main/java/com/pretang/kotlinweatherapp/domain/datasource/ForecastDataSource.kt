package com.pretang.kotlinweatherapp.domain.datasource

import com.pretang.kotlinweatherapp.domain.model.Forecast
import com.pretang.kotlinweatherapp.domain.model.ForecastList

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}