package com.project.speechtotext

import android.annotation.SuppressLint
import android.speech.SpeechRecognizer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {
        fun generateErrorText(errorCode: Int): String {
            when (errorCode) {
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                    return "Recognition Service Busy"
                }
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> {
                    return "Permission Not granted"
                }
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                    return "No Speech Input"
                }
                SpeechRecognizer.ERROR_NETWORK -> {
                    return "Network Error"
                }
                SpeechRecognizer.ERROR_AUDIO -> {
                    return "Audio Recording Error"
                }
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                    return "Network Time Out"
                }
                SpeechRecognizer.ERROR_CLIENT -> {
                    return "Client Side Error"
                }
                SpeechRecognizer.ERROR_NO_MATCH -> {
                    return "No Match"
                }
                SpeechRecognizer.ERROR_SERVER -> {
                    return "Server Error"
                }

                else -> {
                    return "Didn't Understand, Please try again"
                }
            }
        }

        fun getDateAndTime(): String {
            @SuppressLint("SimpleDateFormat") val dfDate: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: String = dfDate.format(Calendar.getInstance().time)
//            @SuppressLint("SimpleDateFormat") val dfTime: DateFormat = SimpleDateFormat("HH-mm")
//            val time: String = dfTime.format(Calendar.getInstance().time)
            return "$date"
        }
    }

}