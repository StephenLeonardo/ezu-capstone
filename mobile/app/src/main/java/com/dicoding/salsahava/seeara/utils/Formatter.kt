package com.dicoding.salsahava.seeara.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}