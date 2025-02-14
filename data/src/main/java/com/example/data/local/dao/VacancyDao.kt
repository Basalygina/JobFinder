package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.VacancyEntity
import com.example.data.local.entity.VacancyWithDetails

@Dao
interface VacancyDao {

    @Transaction
    @Query("SELECT * FROM vacancies")
    suspend fun getFavoritesVacancies(): List<VacancyWithDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperience(experience: com.example.data.local.entity.ExperienceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<com.example.data.local.entity.ScheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<com.example.data.local.entity.QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancyScheduleCrossRefs(crossRefs: List<com.example.data.local.entity.VacancyScheduleCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancyQuestionCrossRefs(crossRefs: List<com.example.data.local.entity.VacancyQuestionCrossRef>)
}