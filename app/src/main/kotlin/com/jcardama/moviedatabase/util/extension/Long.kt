package com.jcardama.moviedatabase.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedDate(format: String, locale: Locale = Locale("en")): String =
        SimpleDateFormat(format, locale).format(Date(this))
