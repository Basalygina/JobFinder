package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val town: String,
    val street: String,
    val house: String
) : Parcelable