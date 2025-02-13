package com.example.jobfinder.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Offer
import com.example.domain.models.Vacancy
import com.example.jobfinder.R
import com.example.jobfinder.databinding.FragmentSearchBinding
import com.example.jobfinder.presentation.search.SearchScreenState
import com.example.jobfinder.presentation.search.SearchViewModel
import com.example.jobfinder.ui.common.SpaceItemDecoration
import com.example.jobfinder.ui.common.VacancyAdapter
import com.example.jobfinder.utils.UiUtils.dpToPx
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val vacancyAdapter by lazy { VacancyAdapter() { selectVacancy(it) } }
    private val offersAdapter by lazy { OffersAdapter() { selectOffer(it) } }

    private var currentFullContent = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.searchScreenState.collect { state ->
                when (state) {
                    is SearchScreenState.Content -> {
                        currentFullContent = state.isFullContent
                        showContent(
                            state.vacancies, state.isFullContent, state.offers
                        )
                    }

                    is SearchScreenState.Error -> showError(state.message)
                    SearchScreenState.Loading -> showLoading()
                }
            }
        }
        with(binding) {
            rvVacancies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (!currentFullContent && lastVisibleItemPosition >= 2) {
                        binding.btnShowMoreVacancies.visibility = View.VISIBLE
                    } else {
                        binding.btnShowMoreVacancies.visibility = View.GONE
                    }
                }
            })

            btnShowMoreVacancies.setOnClickListener {
                viewModel.toggleVacanciesViewMode()
            }

            etSearch.setOnClickListener {
                if (currentFullContent) {
                    viewModel.toggleVacanciesViewMode()
                    etSearch.clearFocus()
                }
            }

            rvVacancies.adapter = vacancyAdapter
            rvVacancies.addItemDecoration(
                SpaceItemDecoration(
                    16.dpToPx(requireContext()),
                    RecyclerView.VERTICAL
                )
            )

            rvOffers.adapter = offersAdapter
            rvOffers.addItemDecoration(
                SpaceItemDecoration(
                    8.dpToPx(requireContext()),
                    RecyclerView.HORIZONTAL
                )
            )
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            tvErrorMessage.visibility = View.GONE

            searchView.visibility = View.GONE
            filterIcon.visibility = View.GONE
            rvOffers.visibility = View.GONE
            tvJobsForYou.visibility = View.GONE
            rvVacancies.visibility = View.GONE
            btnShowMoreVacancies.visibility = View.GONE
            tvTotalCount.visibility = View.GONE
            tvSortOrder.visibility = View.GONE
        }
    }

    private fun showContent(vacancies: List<Vacancy>, fullContent: Boolean, offers: List<Offer>?) {
        Log.d("DTest", "!!showContent, fullContent = $fullContent")
        with(binding) {
            progressBar.visibility = View.GONE
            tvErrorMessage.visibility = View.GONE

            searchView.visibility = View.VISIBLE
            filterIcon.visibility = View.VISIBLE
            rvVacancies.visibility = View.VISIBLE

            if (fullContent) showFullContent(vacancies) else showCompactContent(vacancies, offers)

            vacancyAdapter.submitList(vacancies, fullContent)
        }
    }

    private fun showFullContent(vacancies: List<Vacancy>) {
        with(binding) {
            tvJobsForYou.visibility = View.GONE
            rvOffers.visibility = View.GONE
            btnShowMoreVacancies.visibility = View.GONE
            tvTotalCount.visibility = View.VISIBLE
            tvSortOrder.visibility = View.VISIBLE

            tvTotalCount.text = resources.getQuantityString(
                R.plurals.total_vacancies,
                vacancies.size,
                vacancies.size
            )

            etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_back, 0, 0, 0)
            etSearch.hint = getString(R.string.search_hint_full_content_mode)

            val params = rvVacancies.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = tvTotalCount.id
            params.topMargin = 25.dpToPx(requireContext())
            rvVacancies.layoutParams = params
        }
    }

    private fun showCompactContent(
        vacancies: List<Vacancy>,
        offers: List<Offer>?
    ) {
        with(binding) {
            tvJobsForYou.visibility = View.VISIBLE
            tvTotalCount.visibility = View.GONE
            tvSortOrder.visibility = View.GONE
            btnShowMoreVacancies.text = resources.getQuantityString(
                R.plurals.show_more_vacancies,
                vacancies.size - 3,
                vacancies.size - 3
            )
            if (offers.isNullOrEmpty()) {
                rvOffers.visibility = View.GONE
            } else {
                rvOffers.visibility = View.VISIBLE
                offersAdapter.submitList(offers)
            }

            etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_search, 0, 0, 0)
            etSearch.hint = getString(R.string.search_hint_compact_mode)

            val params = rvVacancies.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = tvJobsForYou.id
            params.topMargin = 16.dpToPx(requireContext())
            rvVacancies.layoutParams = params
        }
    }

    private fun showError(message: String) {
        with(binding) {
            tvErrorMessage.text = message
            progressBar.visibility = View.GONE
            tvErrorMessage.visibility = View.VISIBLE

            searchView.visibility = View.GONE
            filterIcon.visibility = View.GONE
            rvOffers.visibility = View.GONE
            tvJobsForYou.visibility = View.GONE
            rvVacancies.visibility = View.GONE
            btnShowMoreVacancies.visibility = View.GONE
            tvTotalCount.visibility = View.GONE
            tvSortOrder.visibility = View.GONE
        }
    }

    private fun selectVacancy(vacancy: Vacancy) {
        Log.d("DTest", "selectVacancy")

    }

    private fun selectOffer(offer: Offer) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
        context?.startActivity(intent)
    }

}