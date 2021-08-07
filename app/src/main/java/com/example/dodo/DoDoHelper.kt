package com.example.dodo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.proto.DoDoProto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DoDoHelper {
    companion object Factory {
        fun create(): DoDoHelper = DoDoHelper()
    }

    fun grpcToStringDateTime(grpcDateTime: DoDoProto.DateTime): String {

        val timeString = "${grpcDateTime.hour}:${grpcDateTime.minute}:${grpcDateTime.second}"
        val dateString = "${grpcDateTime.year}-${grpcDateTime.month}-${grpcDateTime.day}"

        return "${dateString}T${timeString}"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun stringTogrpcDateTime(stringDateTime: String): DoDoProto.DateTime? {
        // format: yyyy-MM-ddTHH:mm:ss
        val ktDateTime= LocalDateTime.parse(stringDateTime, DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss"))

        val grpcDateTime = DoDoProto.DateTime.newBuilder()
        grpcDateTime.year = ktDateTime.year
        grpcDateTime.month = ktDateTime.monthValue
        grpcDateTime.day = ktDateTime.dayOfMonth
        grpcDateTime.hour = ktDateTime.hour
        grpcDateTime.minute = ktDateTime.minute
        grpcDateTime.second = ktDateTime.second

        return grpcDateTime.build()
    }
}