package com.example.jobfinder.ui.vacancydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.Vacancy
import com.example.jobfinder.MainActivity
import com.example.jobfinder.R
import com.example.jobfinder.databinding.FragmentVacancyDetailsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class VacancyDetailsFragment : Fragment() {
    private val args: VacancyDetailsFragmentArgs by navArgs()
    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding get() = _binding!!
    private val currentVacancy: Vacancy by lazy { args.vacancy }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Скрытие bottom navigation при открытии деталей
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation_view).visibility =
            View.GONE

        with(binding) {
            backImg.setOnClickListener { findNavController().popBackStack() }

            headerVacancy.text = currentVacancy.title
            vacancyTitle.text = currentVacancy.title
            salaryRange.text = currentVacancy.salary.full
            experience.text = currentVacancy.experience.previewText
            schedules.text = currentVacancy.schedules.joinToString(", ")
            company.text = currentVacancy.company
            description.text = currentVacancy.description
            responsibilities.text = currentVacancy.responsibilities

            //Полный адрес
            address.text = listOf(
                currentVacancy.address.town,
                currentVacancy.address.street,
                currentVacancy.address.house
            ).filter { it.isNotBlank() }
                .joinToString(", ")

            //Количесиво людей, просматривающих вакансию
            currentlyWatching.text = if (currentVacancy.lookingNumber == null) {
                getString(R.string.currently_watching_zero)
            } else {
                resources.getQuantityString(
                    R.plurals.currently_watching_details,
                    currentVacancy.lookingNumber!!,
                    currentVacancy.lookingNumber
                )
            }

            //Количесиво людей, откликнувшихся на вакансию
            appliedNumber.text = if (currentVacancy.appliedNumber == null) {
                getString(R.string.applied_number_zero)
            } else {
                resources.getQuantityString(
                    R.plurals.applied_number_details,
                    currentVacancy.appliedNumber!!,
                    currentVacancy.appliedNumber
                )
            }

            //Иконка избранного меняется в зависимости от состояния
            icFavorite.setImageResource(
                if (currentVacancy.isFavorite) R.drawable.ic_favorite_active
                else R.drawable.ic_favorite_not_active
            )

            setUpQuestionsFlowWidget()
        }
    }

    private fun setUpQuestionsFlowWidget() {
        val parentLayout = binding.constraintLayout
        val flow = binding.questionsFlow

        // Удаляем все дочерние view с тегом "questionView"
        parentLayout.children
            .filter { it.tag == "questionView" }
            .toList()
            .forEach { parentLayout.removeView(it) }

        // Инфлейтим новые view для каждого вопроса и собираем их id
        val questionIds = currentVacancy.questions?.map { questionText ->
            LayoutInflater.from(requireContext())
                .inflate(R.layout.item_question_linear, parentLayout, false).also { view ->
                    (view as TextView).apply {
                        tag = "questionView"
                        text = questionText
                        id = View.generateViewId()
                    }
                    parentLayout.addView(view)
                }.id
        }?.toIntArray() ?: intArrayOf()

        flow.referencedIds = questionIds
    }


    override fun onDestroyView() {
        super.onDestroyView()
        //отображаем BottomNavigationView при выходе из фрагмента
        (activity as MainActivity)
            .findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            .visibility = View.VISIBLE
        _binding = null
    }
}