package com.githubstarredusers.view.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun View.visible(isVisible: Boolean, ifNot: Int = View.GONE) {
    visibility = if (isVisible) View.VISIBLE else ifNot
}

fun RecyclerView.clearItemDecorations() {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
}