package com.linkerpad.linkerpad.Data

/**
 * Created by alihajiloo on 8/11/18.
 */

enum class DateType(val value: Int) {
    StartDate(1),
    EndDate(2);

    companion object {
        fun fromInt(value: Int): DateType? {
            when (value) {
                1 -> {
                    return StartDate
                }
                2 -> {
                    return EndDate
                }
                else -> {
                    return null
                }
            }

        }
    }
}
