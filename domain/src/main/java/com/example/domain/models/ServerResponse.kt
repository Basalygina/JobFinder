package com.example.domain.models

data class ServerResponse(
    val offers: List<Offer>,
    val vacancies: List<Vacancy>
)