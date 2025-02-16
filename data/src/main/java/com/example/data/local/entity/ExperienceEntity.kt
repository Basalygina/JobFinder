package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experiences")
data class ExperienceEntity(
    @PrimaryKey
    val experienceId: Int, // (previewText + text).hashCode()
    val previewText: String,
    val text: String
)