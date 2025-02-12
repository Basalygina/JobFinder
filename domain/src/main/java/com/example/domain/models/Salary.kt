package com.example.domain.models

import java.io.Serializable

data class Salary(
    val full: String,
    val short: String? = null
) : Serializable