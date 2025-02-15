package com.example.jobfinder.ui.search

import com.example.domain.models.Offer
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.example.jobfinder.ui.delegates.offerAdapterDelegate

class OffersAdapter(
    private val onOfferClick: (Offer) -> Unit
) : AsyncListDifferDelegationAdapter<Offer>(OfferDiffCallback()) {
    init {
        delegatesManager.addDelegate(offerAdapterDelegate(onOfferClick))
    }

    fun submitList(newOffers: List<Offer>) {
        items = newOffers
    }
}