package com.pretang.kotlinweatherapp.extensions

import android.view.View
import android.widget.TextView

/**
 * @author baizhou
 * @data 2018/1/9
 * @description
 */
fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

var TextView.textColor: Int
    get() = currentTextColor
    set(value) = setTextColor(value)