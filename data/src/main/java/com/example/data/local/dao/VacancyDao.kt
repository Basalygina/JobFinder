package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.ExperienceEntity
import com.example.data.local.entity.QuestionEntity
import com.example.data.local.entity.ScheduleEntity
import com.example.data.local.entity.VacancyEntity
import com.example.data.local.entity.VacancyQuestionCrossRef
import com.example.data.local.entity.VacancyScheduleCrossRef
import com.example.data.local.entity.VacancyWithDetails
import com.example.data.mappers.toEntity
import com.example.data.mappers.toFavoriteDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface VacancyDao {

    @Transaction
    @Query("SELECT * FROM vacancies")
    fun getFavoriteVacancies(): Flow<List<VacancyWithDetails>>

    @Query("DELETE FROM vacancies WHERE id = :id")
    suspend fun deleteFavoriteVacancyById(id: String)

    @Query("SELECT id FROM vacancies")
    suspend fun getFavoriteVacancyIds(): List<String>

    @Query("SELECT * FROM experiences WHERE experienceId = :id LIMIT 1")
    suspend fun getExperienceById(id: Int): ExperienceEntity?

    @Query("SELECT * FROM schedules WHERE scheduleId = :id LIMIT 1")
    suspend fun getScheduleById(id: Int): ScheduleEntity?

    @Query("SELECT * FROM questions WHERE questionId = :id LIMIT 1")
    suspend fun getQuestionById(id: Int): QuestionEntity?

    @Transaction
    suspend fun addFavoriteVacancy(vacancy: com.example.domain.models.Vacancy) {
        val expEntity = vacancy.experience.toEntity()
        val existingExp = getExperienceById(expEntity.experienceId)
        val finalExperienceId = existingExp?.experienceId ?: insertExperience(expEntity).toInt()

        val favoriteDetails = vacancy.toFavoriteDetails(finalExperienceId)
        insertVacancy(favoriteDetails.vacancy)

        favoriteDetails.schedules.forEach { schedule ->
            val existingSchedule = getScheduleById(schedule.scheduleId)
            if (existingSchedule == null) {
                insertSchedule(schedule)
            }
            insertVacancyScheduleCrossRef(VacancyScheduleCrossRef(favoriteDetails.vacancy.id, schedule.scheduleId))
        }

        favoriteDetails.questions.forEach { question ->
            val existingQuestion = getQuestionById(question.questionId)
            if (existingQuestion == null) {
                insertQuestion(question)
            }
            insertVacancyQuestionCrossRef(VacancyQuestionCrossRef(favoriteDetails.vacancy.id, question.questionId))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperience(experience: ExperienceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancyScheduleCrossRef(crossRef: VacancyScheduleCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancyQuestionCrossRef(crossRef: VacancyQuestionCrossRef)
}