package eu.bernardosan.pillz.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastItemMarginItemDecoration(
    private val firstItemMargin: Int = 0, private val lastItemMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    private fun isFirstItem(parent: RecyclerView, view: View): Boolean {
        return parent.getChildAdapterPosition(view) == 0
    }

    private fun isLastItem(parent: RecyclerView, view: View, state: RecyclerView.State): Boolean {
        return parent.getChildAdapterPosition(view) == state.itemCount - 1
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = if (isFirstItem(parent, view)) {
                firstItemMargin
            } else {
                0
            }

            right = if (isLastItem(parent, view, state)) {
                lastItemMargin
            } else {
                0
            }
        }
    }


}