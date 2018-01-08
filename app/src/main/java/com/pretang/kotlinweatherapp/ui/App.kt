package com.pretang.kotlinweatherapp.ui

import android.app.Application
import com.pretang.kotlinweatherapp.domain.delegate.DelegatesExt

/**
 * @author baizhou
 * @data 2018/1/5
 * @description
 */
class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}