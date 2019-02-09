package io.exera.exrtools

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Attila Janosi on 08/02/2019.
 * EXERA SOFTDEVELOP SRL
 * https://exera.io
 */

/**
 * Converts date to string
 * @param format: the date format in String
 */
@SuppressLint("SimpleDateFormat")
fun Date.convertToString(format: String): String {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.format(this)
}

/**
 * Converts date to milliseconds
 */
fun Date.toMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.timeInMillis
}

/**
 * Converts date to Calendar
 */
fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}