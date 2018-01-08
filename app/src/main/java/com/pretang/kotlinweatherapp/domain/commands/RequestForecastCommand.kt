package com.pretang.kotlinweatherapp.domain.commands

import com.pretang.kotlinweatherapp.ForecastRequest
import com.pretang.kotlinweatherapp.domain.ForecastDataMapper
import com.pretang.kotlinweatherapp.domain.model.ForecastList

/**
 * @author baizhou
 * @data 2018/1/5
 * @description
 */
class RequestForecastCommand(private val zipCode: String) : Command<ForecastList> {
    override fun excute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}