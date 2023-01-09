package com.project.speechtotext.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesModel(
    @PrimaryKey
    val id: Int ?= null,
    val text: String = "",

)
