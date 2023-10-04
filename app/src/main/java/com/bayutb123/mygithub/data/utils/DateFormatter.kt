package com.bayutb123.mygithub.data.utils

class DateFormatter {
    companion object {

        fun dateFormat(date: String): String {
            val dateSplit = date.split("-")
            val year = dateSplit[0]
            val month = dateSplit[1]
            val day = dateSplit[2].split("T")[0]
            return "$day-$month-$year"
        }

    }
}