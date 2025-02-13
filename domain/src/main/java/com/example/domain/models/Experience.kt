package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Experience(
    val previewText: String,
    val text: String
) : Parcelable