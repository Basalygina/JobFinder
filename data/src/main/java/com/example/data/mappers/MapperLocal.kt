package com.example.data.mappers

import com.example.data.local.entity.AddressEntity
import com.example.data.local.entity.ExperienceEntity
import com.example.data.local.entity.QuestionEntity
import com.example.data.local.entity.SalaryEntity
import com.example.data.local.entity.ScheduleEntity
import com.example.data.local.entity.VacancyEntity
import com.example.data.local.entity.VacancyWithDetails
import com.example.domain.models.Address
import com.example.domain.models.Experience
import com.example.domain.models.Salary
import com.example.domain.models.Vacancy

fun AddressEntity.toDomain(): Address = Address(
    town = town,
    street = street,
    house = house
)
fun Address.toEntity(): AddressEntity = AddressEntity(
    town = town,
    street = street,
    house = house
)

fun SalaryEntity.toDomain(): Salary = Salary(
    full = full,
    short = short
)
fun Salary.toEntity(): SalaryEntity = SalaryEntity(
    full = full,
    short = short
)

fun ExperienceEntity.toDomain(): Experience = Experience(
    previewText = previewText,
    text = text
)
fun Experience.toEntity(): ExperienceEntity {
    val key = (previewText + text).hashCode()
    return ExperienceEntity(
        experienceId = key,
        previewText = previewText,
        text = text
    )
}

fun QuestionEntity.toDomain(): String = text
fun String.toQuestionEntity(): QuestionEntity {
    return QuestionEntity(
        questionId = this.hashCode(),
        text = this
    )
}

fun ScheduleEntity.toDomain(): String = name
fun String.toScheduleEntity(): ScheduleEntity {
    return ScheduleEntity(
        scheduleId = this.hashCode(),
        name = this
    )
}

fun VacancyEntity.toDomain(
    experience: Experience? = null,
    schedules: List<String> = emptyList(),
    questions: List<String> = emptyList()
): Vacancy {
    return Vacancy(
        id = id,
        lookingNumber = lookingNumber,
        title = title,
        address = address.toDomain(),
        company = company,
        experience = experience ?: Experience(previewText = "", text = ""),
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
fun Vacancy.toEntity(experienceId: Int?): VacancyEntity {
    return VacancyEntity(
        id = id,
        lookingNumber = lookingNumber,
        title = title,
        experienceId = experienceId,
        company = company,
        publishedDate = publishedDate,
        isFavorite = isFavorite,
        appliedNumber = appliedNumber,
        description = description,
        responsibilities = responsibilities,
        address = address.toEntity(),
        salary = salary.toEntity()
    )
}


fun Vacancy.toFavoriteDetails(experienceId: Int): VacancyWithDetails {
    return VacancyWithDetails(
        vacancy = this.toEntity(experienceId),
        experience = this.experience.toEntity(),
        schedules = this.schedules.map { it.toScheduleEntity() },
        questions = (this.questions ?: emptyList()).map { it.toQuestionEntity() }
    )
}

fun VacancyWithDetails.toDomain(): Vacancy {
    return vacancy.toDomain(
        experience = experience?.toDomain(),
        schedules = schedules.map { it.toDomain() },
        questions = questions.map { it.toDomain() }
    )
}




