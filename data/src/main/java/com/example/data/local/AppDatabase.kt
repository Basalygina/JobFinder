package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.VacancyDao
import com.example.data.local.entity.ExperienceEntity
import com.example.data.local.entity.QuestionEntity
import com.example.data.local.entity.ScheduleEntity
import com.example.data.local.entity.VacancyEntity
import com.example.data.local.entity.VacancyQuestionCrossRef
import com.example.data.local.entity.VacancyScheduleCrossRef

@Database(
    entities = [
        VacancyEntity::class,
        ExperienceEntity::class,
        ScheduleEntity::class,
        VacancyScheduleCrossRef::class,
        QuestionEntity::class,
        VacancyQuestionCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}