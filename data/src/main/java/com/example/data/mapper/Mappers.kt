package com.example.data.mapper

import com.example.data.dto.AddressDto
import com.example.data.dto.ExperienceDto
import com.example.data.dto.OfferDto
import com.example.data.dto.SalaryDto
import com.example.data.dto.ServerResponseDto
import com.example.data.dto.VacancyDto
import com.example.domain.models.Address
import com.example.domain.models.Experience
import com.example.domain.models.Offer
import com.example.domain.models.Salary
import com.example.domain.models.ServerResponse
import com.example.domain.models.Vacancy

fun VacancyDto.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        lookingNumber = lookingNumber,
        title = title,
        address = address.toDomain(),
        company = company,
        experience = experience.toDomain(),
        publishedDate = publishedDate,
        isFavorite = isFavorite,
        salary = salary.toDomain(),
        schedules = schedules,
        appliedNumber = appliedNumber,
        description = description,
        responsibilities = responsibilities,
        questions = questions
    )
}

fun ServerResponseDto.toDomain(): ServerResponse {
    return ServerResponse(
        offers = offers.map { it.toDomain() },
        vacancies = vacancies.map { it.toDomain() }
    )
}

fun OfferDto.toDomain(): Offer {
    return Offer(
        id = id ?: "",
        title = title,
        link = link,
        button = button?.text ?: ""
    )
}

fun AddressDto.toDomain(): Address {
    return Address(
        town = town,
        street = street,
        house = house
    )
}

fun ExperienceDto.toDomain(): Experience {
    return Experience(
        previewText = previewText,
        text = text
    )
}

fun SalaryDto.toDomain(): Salary {
    return Salary(
        full = full,
        short = short
    )
}
