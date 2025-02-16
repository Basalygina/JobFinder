package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "vacancy_question_cross_ref",
    primaryKeys = ["vacancyId", "questionId"],
    foreignKeys = [
        ForeignKey(
            entity = VacancyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vacancyId"],
            onDelete = ForeignKey.CASCADE // При удалении вакансии удаляются и связанные вопросы
        ),
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE // При удалении вопроса удаляются все связи с вакансиями
        )
    ],
    indices = [Index("vacancyId"), Index("questionId")]
)
data class VacancyQuestionCrossRef(
    val vacancyId: String,
    val questionId: Int
)