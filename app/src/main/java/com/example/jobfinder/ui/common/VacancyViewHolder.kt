package com.example.jobfinder.ui.common

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Vacancy
import com.example.jobfinder.R
import com.example.jobfinder.databinding.ItemVacancyLinearBinding
import com.example.jobfinder.utils.DateUtils

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemVacancyLinearBinding.bind(itemView)

    fun bind(model: Vacancy) {
        with(binding) {
            vacancyTitle.text = model.title
            salaryRange.text = model.salary.full
            town.text = model.address.town
            company.text = model.company
            experience.text = model.experience.previewText
            publishedDate.text = itemView.context.getString(
                R.string.published_date,
                DateUtils.formatDateWithGenitive(model.publishedDate)
            )
            if (model.lookingNumber != null) {
                currentlyWatching.text = itemView.context.resources.getQuantityString(
                    R.plurals.currently_watching,
                    model.lookingNumber!!,
                    model.lookingNumber
                )
                currentlyWatching.visibility = View.VISIBLE
            } else {
                currentlyWatching.visibility = View.GONE
            }

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.rootLayout)
            if (binding.currentlyWatching.visibility == View.GONE) {
                constraintSet.connect(
                    binding.vacancyTitle.id, ConstraintSet.TOP,
                    binding.iconFavorite.id, ConstraintSet.TOP, 0
                )
            } else {
                constraintSet.connect(
                    binding.vacancyTitle.id, ConstraintSet.TOP,
                    binding.currentlyWatching.id, ConstraintSet.BOTTOM, 0
                )
            }
            constraintSet.applyTo(binding.rootLayout)
        }
    }

}
