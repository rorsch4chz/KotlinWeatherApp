package com.pretang.kotlinweatherapp.data.db

import com.pretang.kotlinweatherapp.domain.datasource.ForecastDataSource
import com.pretang.kotlinweatherapp.domain.model.Forecast
import com.pretang.kotlinweatherapp.domain.model.ForecastList
import com.pretang.kotlinweatherapp.extensions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
class ForecastDb(
        val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
        val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id).parseOpt { DayForecast(HashMap(it)) }
        forecast?.let { dataMapper.convertDayToDomain(it) }
    }

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? " + "AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        city?.let { dataMapper.convertToDomain(it) }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)
        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }

}