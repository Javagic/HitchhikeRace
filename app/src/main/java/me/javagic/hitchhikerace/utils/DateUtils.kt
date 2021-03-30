package me.javagic.hitchhikerace.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import me.javagic.hitchhikerace.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun EditText.showDatePickerDialogOnClick(
    activity: Activity,
    listener: (Date, EditText) -> Unit,
    calendar: Calendar = Calendar.getInstance()
) = setOnClickListener {
    DatePickerDialog(
        activity,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            listener(calendar.time, this)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
        .apply { setCancelable(true) }
        .apply { datePicker.minDate = Calendar.getInstance().timeInMillis }
        .show()
}

val Context.dateTimeFormat: SimpleDateFormat
    get() =
        SimpleDateFormat(getString(R.string.date_time_format_long_year), Locale("RU"))

val Context.dateFormat: SimpleDateFormat
    get() =
        SimpleDateFormat(getString(R.string.date_format), Locale("RU"))
val Context.timeFormat: SimpleDateFormat
    get() =
        SimpleDateFormat(getString(R.string.time_format), Locale("RU"))

fun DateFormat.parseOrNull(date: String): Date? = try {
    this.parse(date)
} catch (e: ParseException) {
    e.printStackTrace()
    null
}
