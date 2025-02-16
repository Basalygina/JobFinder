package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "vacancy_schedule_cross_ref",
    primaryKeys = ["vacancyId", "scheduleId"],
    foreignKeys = [
        ForeignKey(
            entity = VacancyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vacancyId"],
            onDelete = ForeignKey.CASCADE // При удалении вакансии удаляются и связанные графики работы
        ),
        ForeignKey(
            entity = ScheduleEntity::class,
            parentColumns = ["scheduleId"],
            childColumns = ["scheduleId"],
            onDelete = ForeignKey.CASCADE // При удалении графика работы удаляются все связи с вакансиями
        )
    ],
    indices = [Index("vacancyId"), Index("scheduleId")]
)
data class VacancyScheduleCrossRef(
    val vacancyId: String,
    val scheduleId: Int
)