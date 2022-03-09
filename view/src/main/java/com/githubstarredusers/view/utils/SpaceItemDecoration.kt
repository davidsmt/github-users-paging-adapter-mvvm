package com.githubstarredusers.view.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val spaceSize: Int,
    private val orientation: Int = LinearLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                bottom = spaceSize
            } else {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = spaceSize
                }
                right = spaceSize
            }
        }
    }

}