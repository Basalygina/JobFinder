package com.example.data.dto

data class ServerResponseDto(
    val offers: List<OfferDto>,
    val vacancies: List<VacancyDto>
) : Response()