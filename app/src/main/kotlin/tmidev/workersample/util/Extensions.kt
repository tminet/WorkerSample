package tmidev.workersample.util

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun isAndroid8OrUp(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun isAndroid12OrUp(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

fun Long.toFormattedTimestamp(
    pattern: String = "yyyy/MMM/dd' - 'HH:mm:ss",
    locale: Locale = Locale.getDefault()
): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat(pattern, locale)
    return dateFormat.format(date)
}