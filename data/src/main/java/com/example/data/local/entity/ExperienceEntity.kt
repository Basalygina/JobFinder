package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experience")
data class ExperienceEntity(
    @PrimaryKey(autoGenerate = true)
    val experienceId: Int = 0,
    val previewText: String,
    val text: String
)