package com.example.jobfinder.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// Кастомный декор для отступов между элементами RecyclerView
class SpaceItemDecoration(
    private val space: Int,
    private val orientation: Int // RecyclerView.VERTICAL или RecyclerView.HORIZONTAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Логика расчета отступов в зависимости от ориентации
        if (orientation == RecyclerView.HORIZONTAL) {
            outRect.right = space
        } else { // RecyclerView.VERTICAL
            outRect.bottom = space
        }
    }
}
