package com.project.speechtotext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class Permissions {
    companion object {
        fun checkVoiceRecordingPermission(context: Context) : Boolean{
            val result = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun checkWritePermission(context: Context) : Boolean{
            val result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun checkCameraPermission(context: Context?): Boolean {
            val result = ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA)
            return result == PackageManager.PERMISSION_GRANTED
        }


    }
}