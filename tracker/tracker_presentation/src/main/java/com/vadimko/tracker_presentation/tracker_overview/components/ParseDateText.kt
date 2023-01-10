package com.vadimko.tracker_presentation.tracker_overview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vadimko.tracker_presentation.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ParseDateText(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> stringResource(id = R.string.today)
        today.plusDays(1) -> stringResource(id = R.string.tomorrow)
        today.minusDays(1) -> stringResource(id = R.string.yesterday)
        else -> {
            if (today.year == date.year) DateTimeFormatter.ofPattern("dd LLLL").format(date)
            else DateTimeFormatter.ofPattern("dd LLLL yyyy").format(date)
        }
    }
}