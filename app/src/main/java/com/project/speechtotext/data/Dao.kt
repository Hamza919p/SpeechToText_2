package com.project.speechtotext.data

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {

    @Insert
    fun saveSpeechToText(speechToTextModel: SpeechToTextModel)

    @Insert
    fun saveNotes(notesModel: NotesModel)

    @Query("SELECT * from SpeechToTextModel")
    fun getAllSpeechTexts(): List<SpeechToTextModel>

    @Query("SELECT * from NotesModel")
    fun getAllNotes(): List<NotesModel>
}