package com.project.speechtotext.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SpeechToTextModel::class, NotesModel::class], version = 1)
abstract class SpeechToTextDatabase : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        private var instance: SpeechToTextDatabase?= null

        @Synchronized
        fun getInstance(context: Context) : SpeechToTextDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, SpeechToTextDatabase::class.java,
                    "SpeechToTextDatabase")
                    .build()
            }

            return instance!!
        }

    }
}