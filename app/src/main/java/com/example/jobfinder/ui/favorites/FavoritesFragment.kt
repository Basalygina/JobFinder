package com.example.jobfinder.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Vacancy
import com.example.jobfinder.R
import com.example.jobfinder.databinding.FragmentFavoritesBinding
import com.example.jobfinder.presentation.favorites.FavoritesScreenState
import com.example.jobfinder.presentation.favorites.FavoritesViewModel
import com.example.jobfinder.ui.common.SpaceItemDecoration
import com.example.jobfinder.ui.common.VacancyAdapter
import com.example.jobfinder.utils.UiUtils.dpToPx
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModel()
    private val vacancyAdapter by lazy {
        VacancyAdapter(
            onFavoriteClick = ::onFavoriteClick,
            onItemClick = ::selectVacancy
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        with(binding) {
            favoritesRecycler.adapter = vacancyAdapter
            favoritesRecycler.addItemDecoration(
                SpaceItemDecoration(
                    16.dpToPx(requireContext()),
                    RecyclerView.VERTICAL
                )
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is FavoritesScreenState.Loading -> showLoading()
                    is FavoritesScreenState.Empty -> showEmpty()
                    is FavoritesScreenState.Error -> showError()
                    is FavoritesScreenState.Content -> showContent(state.vacancies)
                }
            }
        }
    }

    private fun showContent(vacancies: List<Vacancy>) {
        with(binding) {
            favoritesCount.text = resources.getQuantityString(
                R.plurals.total_vacancies,
                vacancies.size,
                vacancies.size
            )
            progressBar.visibility = View.GONE
            errorMessage.visibility = View.GONE
            favoritesCount.visibility = View.VISIBLE
            favoritesRecycler.visibility = View.VISIBLE
            vacancyAdapter.submitList(vacancies, showAll = true)
        }
    }

    private fun showError() {
        with(binding) {
            errorMessage.text = getString(R.string.database_error)
            progressBar.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            favoritesCount.visibility = View.GONE
            favoritesRecycler.visibility = View.GONE
        }
    }

    private fun showEmpty() {
        with(binding) {
            errorMessage.text = getString(R.string.database_empty)
            progressBar.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            favoritesCount.visibility = View.GONE
            favoritesRecycler.visibility = View.GONE
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            errorMessage.visibility = View.GONE
            favoritesCount.visibility = View.GONE
            favoritesRecycler.visibility = View.GONE
        }
    }

    private fun selectVacancy(vacancy: Vacancy) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToVacancyDetailsFragment(vacancy)
        findNavController().navigate(action)
    }

    private fun onFavoriteClick(vacancy: Vacancy) {
        viewModel.toggleFavorite(vacancy)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
