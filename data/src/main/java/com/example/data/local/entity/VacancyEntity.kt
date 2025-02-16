package com.example.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vacancies",
    foreignKeys = [
        ForeignKey(
            entity = ExperienceEntity::class,
            parentColumns = ["experienceId"],
            childColumns = ["experienceId"],
            onDelete = ForeignKey.SET_NULL // При удалении опыта работы устанавливаем NULL
        )
    ],
    indices = [Index(value = ["experienceId"])]
)
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val lookingNumber: Int? = null,
    val title: String,
    val experienceId: Int?,
    val company: String,
    val publishedDate: String,
    val isFavorite: Boolean,
    val appliedNumber: Int? = null,
    val description: String? = null,
    val responsibilities: String? = null,
    @Embedded(prefix = "address_")
    val address: AddressEntity,
    @Embedded(prefix = "salary_")
    val salary: SalaryEntity,
)
