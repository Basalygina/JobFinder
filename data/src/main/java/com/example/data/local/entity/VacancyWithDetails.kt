package com.example.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class VacancyWithDetails(
    @Embedded val vacancy: VacancyEntity,
    @Relation(
        parentColumn = "experienceId",
        entityColumn = "experienceId"
    )
    val experience: ExperienceEntity? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "scheduleId",
        associateBy = Junction(
            value = VacancyScheduleCrossRef::class,
            parentColumn = "vacancyId",
            entityColumn = "scheduleId"
        )
    )
    val schedules: List<ScheduleEntity> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId",
        associateBy = Junction(
            value = VacancyQuestionCrossRef::class,
            parentColumn = "vacancyId",
            entityColumn ="questionId"
        )
    )
    val questions: List<QuestionEntity> = emptyList()
)