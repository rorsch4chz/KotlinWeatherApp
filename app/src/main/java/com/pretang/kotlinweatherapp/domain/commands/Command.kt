package com.pretang.kotlinweatherapp.domain.commands

/**
 * @author baizhou
 * @data 2018/1/5
 * @description
 */
public interface Command<T> {
    fun excute(): T
}