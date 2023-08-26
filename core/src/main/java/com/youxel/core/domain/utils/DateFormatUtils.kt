package com.youxel.core.domain.utils

import android.annotation.SuppressLint
import android.text.format.DateUtils
import com.youxel.core.domain.entities.enums.DateDifference
import com.youxel.core.domain.entities.enums.DateType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*


/**
 * Created by Nader Nabil on 29/10/2020.
 */

fun convertDateStringToMilliSeconds(
    dateFromApi: String, inputDateTemplate: String
): Long {
    try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.US)
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        val calendar = Calendar.getInstance()
        calendar.time = apiDate
        return calendar.time.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0
}

fun convertDateStringToDate(
    dateFromApi: String,
    inputDateTemplate: String = "yyyy-MM-dd'T'HH:mm:ss"
): Date? {
    val format = SimpleDateFormat(inputDateTemplate, Locale.US)
    return try {
        format.parse(dateFromApi)
    } catch (e: ParseException) {
        e.printStackTrace()
        Date()
    }
}

fun changeDateFormat(
    dateFromApi: String,
    inputDateTemplate: String,
    outputDateTemplate: String,
    timeZone: String? = null
): String {
    return try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.US)
        timeZone?.let {
            inputSimpleDateFormat.timeZone = TimeZone.getTimeZone(it)
        }
        val outputSimpleDateFormat = SimpleDateFormat(outputDateTemplate, Locale.US)
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        outputSimpleDateFormat.format(apiDate)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToStringDate(date: Date, outputDateFormat: String): String {
    return try {
        val outputSimpleDateFormat = SimpleDateFormat(outputDateFormat, Locale.US)
        outputSimpleDateFormat.timeZone = TimeZone.getDefault()
        outputSimpleDateFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToStringDateLocalized(date: Date, outputDateFormat: String): String {
    return try {
        val outputSimpleDateFormat = SimpleDateFormat(outputDateFormat, Locale.getDefault())
        outputSimpleDateFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToStringDateLocalized(
    dateFromApi: String, inputDateTemplate: String, outputDateTemplate: String
): String {
    return try {
        val inputSimpleDateFormat = SimpleDateFormat(inputDateTemplate, Locale.US)
        val apiDate = inputSimpleDateFormat.parse(dateFromApi) ?: Date()
        val outputSimpleDateFormat = SimpleDateFormat(outputDateTemplate, Locale.getDefault())
        outputSimpleDateFormat.format(apiDate)
    } catch (e: Exception) {
        ""
    }
}

fun convertDateToRelativeDate(
    dateStr: String, dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"
): String {

    val inputSimpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)

    val date = inputSimpleDateFormat.parse(dateStr) ?: Date()

    return DateUtils.getRelativeTimeSpanString(
        date.time, Calendar.getInstance().timeInMillis, DateUtils.SECOND_IN_MILLIS
    ).toString()
}

@SuppressLint("SimpleDateFormat")
fun convertDateToStringDate(
    calender: Calendar, outputDateFormat: String, locale: Locale = Locale.getDefault()
): String {
    return try {
        SimpleDateFormat(outputDateFormat, locale).format(calender.timeInMillis)
    } catch (e: Exception) {
        ""
    }
}

fun getDateDifference(
    startDate: String = "", endDate: String = "", dateDifference: DateDifference, dateFormat: String
): String {

    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())

    try {
        val d1 = sdf.parse(startDate)
        val d2 = sdf.parse(endDate)
        // Calculate time difference in milliseconds
        val differenceInTime = d2.time - d1.time

        return when (dateDifference) {
            DateDifference.SECONDS -> ((differenceInTime / 1000) % 60).toString()
            DateDifference.MINUTES -> ((differenceInTime / (1000 * 60)) % 60).toString()
            DateDifference.HOURS -> ((differenceInTime / (1000 * 60 * 60)) % 24).toString()
            DateDifference.DAYS -> ((differenceInTime / (1000 * 60 * 60 * 24)) % 365).toString()
            DateDifference.YEARS -> (differenceInTime / (1000L * 60 * 60 * 24 * 365)).toString()
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }
}

fun getDurationCalender(
    startDate: String = "", endDate: String = "", dateFormat: String
): Pair<Calendar, Calendar> {
    val sdf = SimpleDateFormat(dateFormat, Locale.US)
    try {
        val d1 = sdf.parse(startDate)
        val d2 = sdf.parse(endDate)
        val startDateCalender = Calendar.getInstance()
        startDateCalender.time = d1
        val endDateCalender = Calendar.getInstance()
        endDateCalender.time = d2
        return Pair(startDateCalender, endDateCalender)
    } catch (e: ParseException) {
        e.printStackTrace()
        return Pair(Calendar.getInstance(), Calendar.getInstance())
    }
}


fun getCurrentDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
    return sdf.format(Date())
}

fun getCurrentDateFormatted(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date())
}


fun getEndDateOfTheYearFormatted(): String {
    // Create last day of year
    val lastDayOfCurrentYear = Calendar.getInstance()
    lastDayOfCurrentYear[Calendar.DATE] = 31
    lastDayOfCurrentYear[Calendar.MONTH] = 11
    return convertDateToStringDate(lastDayOfCurrentYear, "dd/MM/yyyy", Locale.US)
}

fun getDateByType(date: String, dateType: DateType, regex: String): Int {
    val parsedDate = date.split(regex)
    return when (dateType) {
        DateType.DAY -> parsedDate[0].toInt()
        DateType.MONTH -> parsedDate[1].toInt()
        DateType.YEAR -> parsedDate[2].toInt()
    }
}

fun getAddedDate(date: String, amount: Int): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val c = Calendar.getInstance()
    c.time = sdf.parse(date)
    c.add(Calendar.DATE, amount) // number of days to add
    return sdf.format(c.time)
}

fun getLastDayOfMonthDate(month: Int): String {
    val calendar = Calendar.getInstance()
    calendar[Calendar.MONTH] = month - 1
    calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DATE)
    val date = calendar.time
    val dateFormat = SimpleDateFormat("dd/MM", Locale.US)
    return dateFormat.format(date)
}

fun getCurrentYear(): Int {
    return Year.now().value
    /*val now = Calendar.getInstance()
    val year = now[Calendar.YEAR]
    return year*/
}

fun getCurrentAm(): Boolean {
    val now = Calendar.getInstance()
    return now[Calendar.AM_PM] == Calendar.AM
}

fun convertDateStringToMilliSeconds(date: String): Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val convertedDate = sdf.parse(date)
    return convertedDate.time
}

fun changeDateToFormat(outPutFormat: String, date: Date): String {
    val outputSimpleDateFormat = SimpleDateFormat(outPutFormat, Locale.US)
    return outputSimpleDateFormat.format(date)
}


fun changeDateToEnglishFormat(outPutFormat: String, date: Date): String {
    val outputSimpleDateFormat = SimpleDateFormat(outPutFormat, Locale.ENGLISH)
    return outputSimpleDateFormat.format(date)
}

fun isYesterday(whenInMillis: Long): Boolean {
    return DateUtils.isToday(whenInMillis + DateUtils.DAY_IN_MILLIS)
}

fun isPastDay(
    date: String, inputFormat: String,
): Boolean {
    val selectedDate = convertStringDateToCalender(date, inputFormat)[Calendar.DAY_OF_YEAR]
    val currentCalendar = Calendar.getInstance()[Calendar.DAY_OF_YEAR]
    return selectedDate < currentCalendar
}

fun isToday(
    date: String, inputFormat: String,
): Boolean {
    val selectedDate = convertStringDateToCalender(date, inputFormat)[Calendar.DAY_OF_YEAR]
    val currentCalendar = Calendar.getInstance()[Calendar.DAY_OF_YEAR]
    return selectedDate == currentCalendar
}

fun isPastTime(
    time: String, inputFormat: String,
): Boolean {
    val selectedHour = convertStringDateToCalender(time, inputFormat)[Calendar.HOUR_OF_DAY]
    val currentHour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
    val selectedMin = convertStringDateToCalender(time, inputFormat)[Calendar.MINUTE]
    val currentMin = Calendar.getInstance()[Calendar.MINUTE]
    if (selectedHour < currentHour) {
        return true
    } else if (selectedHour == currentHour) {
        return selectedMin < currentMin
    }
    return false
}

fun convertDateToCalender(date: Date): Calendar {
    val cal = Calendar.getInstance()
    cal.time = date
    return cal
}

fun convertStringDateToCalender(
    date: String,
    inputFormat: String,
): Calendar {
    return try {
        val dateFormat = SimpleDateFormat(inputFormat, Locale.US)
        val parsedDate = dateFormat.parse(date)
        val calendar = Calendar.getInstance()
        if (parsedDate != null) {
            calendar.time = parsedDate
        }
        calendar
    } catch (e: Exception) {
        Calendar.getInstance()
    }
}

fun convertStringDateToCalender(
    date: String,
    inputFormat: String,
    outputFormat: String,
): Calendar {
    return try {
        val inputSimpleDateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
        val apiDate = inputSimpleDateFormat.parse(date) ?: Date()
        val outputSimpleDateFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
        outputSimpleDateFormat.format(apiDate)
        val calendar = Calendar.getInstance()
        calendar.time = apiDate
        calendar
    } catch (e: Exception) {
        Calendar.getInstance()
    }
}

