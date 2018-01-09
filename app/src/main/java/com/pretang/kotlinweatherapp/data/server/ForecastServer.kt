package com.pretang.kotlinweatherapp.data.server

import com.pretang.kotlinweatherapp.data.db.ForecastDb
import com.pretang.kotlinweatherapp.domain.datasource.ForecastDataSource
import com.pretang.kotlinweatherapp.domain.model.ForecastList

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}