package com.example.dodo

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class DoDoHelper {
    companion object Factory {
        fun create(): DoDoHelper = DoDoHelper()
    }

    /*fun grpcToStringDateTime(grpcDateTime: DoDoProto.DateTime): String {

        val timeString = "${grpcDateTime.hour}:${grpcDateTime.minute}:${grpcDateTime.second}"
        val dateString = "${grpcDateTime.year}-${grpcDateTime.month}-${grpcDateTime.day}"

        return "${dateString}T${timeString}"
    }*/


    /*@RequiresApi(Build.VERSION_CODES.O)
    fun ktDateTimeTogrpcDateTime(ktDateTime: LocalDateTime): DoDoProto.DateTime? {
        // format: yyyy-MM-ddTHH:mm:ss

        val grpcDateTime = DoDoProto.DateTime.newBuilder()
        grpcDateTime.year = ktDateTime.year
        grpcDateTime.month = ktDateTime.monthValue
        grpcDateTime.day = ktDateTime.dayOfMonth
        grpcDateTime.hour = ktDateTime.hour
        grpcDateTime.minute = ktDateTime.minute
        grpcDateTime.second = ktDateTime.second

        return grpcDateTime.build()
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToDateTime(stringDT: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDT = LocalDateTime.parse(stringDT, formatter)
        return  localDT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDatetimeString(): String {
        println(Calendar.getInstance().toString())
        val currentDatetime = LocalDateTime.now().withNano(0)

        return currentDatetime.toString()
    }
}