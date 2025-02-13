package com.example.jobfinder.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val space: Int,
    private val orientation: Int // RecyclerView.VERTICAL или RecyclerView.HORIZONTAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == RecyclerView.HORIZONTAL) {
            outRect.right = space
        } else { // RecyclerView.VERTICAL
            outRect.bottom = space
        }
    }
}
