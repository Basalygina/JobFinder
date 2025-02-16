package com.example.jobfinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.jobfinder.databinding.ActivityMainBinding
import com.example.jobfinder.presentation.favorites.FavoritesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val favoritesViewModel: FavoritesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = systemBars.top)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesViewModel.favoritesCount.collect { count ->
                    updateFavoritesBadge(binding, count)
                }
            }
        }
    }

    private fun updateFavoritesBadge(binding: ActivityMainBinding, count: Int) {
        val badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.favoritesFragment)

        if (count > 0) {
            badgeDrawable.isVisible = true
            badgeDrawable.number = count
        } else {
            badgeDrawable.isVisible = false
        }
    }
}