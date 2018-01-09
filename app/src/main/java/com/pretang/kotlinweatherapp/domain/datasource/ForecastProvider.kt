package com.pretang.kotlinweatherapp.domain.datasource

import com.pretang.kotlinweatherapp.data.db.ForecastDb
import com.pretang.kotlinweatherapp.data.server.ForecastServer
import com.pretang.kotlinweatherapp.domain.model.Forecast
import com.pretang.kotlinweatherapp.domain.model.ForecastList
import com.pretang.kotlinweatherapp.extensions.firstResult

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }

}