package com.example.domain.models

import java.io.Serializable


data class Offer(
    val id: String? = null,
    val title: String,
    val link: String,
    val button: String? = null
) : Serializable {
    companion object {
        const val NEAR_VACANCIES = "near_vacancies"
        const val LEVEL_UP_RESUME = "level_up_resume"
        const val TEMPORARY_JOB = "temporary_job"
    }
}