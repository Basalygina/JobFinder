package com.example.jobfinder.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Offer
import com.example.jobfinder.databinding.ItemOfferLinearBinding

class OffersAdapter(
    private var offers: List<Offer> = emptyList(),
    private val clickListener: OffersClickListener
    ) : RecyclerView.Adapter<OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.bind(offer)
        holder.itemView.setOnClickListener { clickListener.onOfferClick(offers.get(position)) }
    }

    override fun getItemCount(): Int = offers.size

    fun submitList(newOffers: List<Offer>) {
        offers = newOffers
        notifyDataSetChanged()
    }

    fun interface OffersClickListener {
        fun onOfferClick(offer: Offer)
    }
}
