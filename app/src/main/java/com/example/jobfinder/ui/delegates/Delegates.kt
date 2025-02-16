package com.example.jobfinder.ui.delegates

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import com.example.domain.models.Offer
import com.example.domain.models.Offer.Companion.LEVEL_UP_RESUME
import com.example.domain.models.Offer.Companion.NEAR_VACANCIES
import com.example.domain.models.Offer.Companion.TEMPORARY_JOB
import com.example.domain.models.Vacancy
import com.example.jobfinder.R
import com.example.jobfinder.databinding.ItemOfferLinearBinding
import com.example.jobfinder.databinding.ItemVacancyLinearBinding
import com.example.jobfinder.utils.DateUtils
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

// Делегат для отображения элементов вакансий
fun vacancyAdapterDelegate(
    onFavoriteClick: (Vacancy) -> Unit,
    onItemClick: (Vacancy) -> Unit
) = adapterDelegateViewBinding<Vacancy, Vacancy, ItemVacancyLinearBinding>(
    { layoutInflater, parent -> ItemVacancyLinearBinding.inflate(layoutInflater, parent, false) }
) {
    binding.iconFavorite.setOnClickListener { onFavoriteClick(item) }
    binding.root.setOnClickListener { onItemClick(item) }

    bind {
        binding.apply {
            vacancyTitle.text = item.title
            salaryRange.text = item.salary.full
            town.text = item.address.town
            company.text = item.company
            experience.text = item.experience.previewText
            publishedDate.text = itemView.context.getString(
                R.string.published_date,
                DateUtils.formatDateWithGenitive(item.publishedDate)
            )
            if (item.lookingNumber != null) {
                currentlyWatching.text = itemView.context.resources.getQuantityString(
                    R.plurals.currently_watching,
                    item.lookingNumber!!,
                    item.lookingNumber
                )
                currentlyWatching.visibility = View.VISIBLE
            } else {
                currentlyWatching.visibility = View.GONE
            }

            val favoriteIconRes =
                if (item.isFavorite) R.drawable.ic_favorite_active
                else R.drawable.ic_favorite_not_active
            iconFavorite.setImageResource(favoriteIconRes)

            // Настройки отступов в зависимости от наличия информации о просмотрах
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

// Делегат для отображения элементов из блока рекомендаций
fun offerAdapterDelegate(
    onOfferClick: (Offer) -> Unit
) = adapterDelegateViewBinding<Offer, Offer, ItemOfferLinearBinding>(
    { layoutInflater, parent -> ItemOfferLinearBinding.inflate(layoutInflater, parent, false) }
) {
    binding.root.setOnClickListener { onOfferClick(item) }
    bind {
        binding.apply {
            titleOffer.text = item.title.trim()
            if (item.button.isNullOrBlank()) {
                titleOffer.maxLines = 3
            } else {
                titleOffer.maxLines = 2
                btnOffer.text = item.button
            }

            val iconResId = when (item.id) {
                NEAR_VACANCIES -> R.drawable.ic_vacancies_near
                LEVEL_UP_RESUME -> R.drawable.ic_resume_up
                TEMPORARY_JOB -> R.drawable.ic_temporary_job
                else -> null
            }
            if (iconResId != null) {
                iconOffer.visibility = View.VISIBLE
                iconOffer.setImageResource(iconResId)
            } else {
                iconOffer.visibility = View.INVISIBLE
            }
        }
    }
}

