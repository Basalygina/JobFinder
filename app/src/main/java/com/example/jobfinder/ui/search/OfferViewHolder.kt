package com.example.jobfinder.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Offer
import com.example.domain.models.Offer.Companion.LEVEL_UP_RESUME
import com.example.domain.models.Offer.Companion.NEAR_VACANCIES
import com.example.domain.models.Offer.Companion.TEMPORARY_JOB
import com.example.jobfinder.R
import com.example.jobfinder.databinding.ItemOfferLinearBinding

class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemOfferLinearBinding.bind(itemView)

    fun bind(model: Offer) {
        with(binding) {
            titleOffer.text = model.title.trim()
            btnOffer.text = model.button

            val iconResId = when (model.id) {
                NEAR_VACANCIES -> R.drawable.ic_vacancies_near
                LEVEL_UP_RESUME -> R.drawable.ic_resume_up
                TEMPORARY_JOB -> R.drawable.ic_temporary_job
                else -> null
            }
            if (iconResId != null) {
                iconOffer.visibility = View.VISIBLE
                iconOffer.setImageResource(iconResId)
            } else {
                iconOffer.visibility = View.GONE
            }

        }
    }

}
