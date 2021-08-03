package com.example.dodo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.proto.DoDoProto
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DoDoHelper {
    companion object Factory {
        fun create(): DoDoHelper = DoDoHelper()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun grpcToKtDatetime(grpcDateTime: DoDoProto.DateTime): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("ss:mm:HH dd-MM-yyyy")

        val timeString = "${grpcDateTime.second}:${grpcDateTime.minute}:${grpcDateTime.hour}"
        val dateString ="${grpcDateTime.day}-${grpcDateTime.month}-${grpcDateTime.year}"
        val datetimeString = "$timeString $dateString"

        return try {
            LocalDateTime.parse(datetimeString, formatter)
        } catch (e: Exception) {
            LocalDateTime.now()
        }
    }
}