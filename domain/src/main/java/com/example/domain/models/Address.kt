package com.example.domain.models

import java.io.Serializable


data class Address(
    val town: String,
    val street: String,
    val house: String
) : Serializable