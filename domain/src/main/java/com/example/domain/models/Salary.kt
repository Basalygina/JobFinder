package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Salary(
    val full: String,
    val short: String? = null
) : Parcelable