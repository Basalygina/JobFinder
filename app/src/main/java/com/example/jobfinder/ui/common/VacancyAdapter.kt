package com.example.jobfinder.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Vacancy
import com.example.jobfinder.databinding.ItemVacancyLinearBinding


class VacancyAdapter(
    private var vacancies: List<Vacancy> = emptyList(),
    private var isFullContent: Boolean = false,
    private val clickListener: VacancyClickListener,

    ) : RecyclerView.Adapter<VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = ItemVacancyLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener { clickListener.onVacancyClick(vacancies.get(position)) }
    }

    override fun getItemCount(): Int = vacancies.size

    fun submitList(newVacancies: List<Vacancy>, showAll: Boolean) {
        isFullContent = showAll
        vacancies = if (showAll) newVacancies else newVacancies.take(3)
        notifyDataSetChanged()
    }

    fun interface VacancyClickListener {
        fun onVacancyClick(vacancy: Vacancy)
    }
}
