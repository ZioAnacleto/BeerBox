package com.zioanacleto.beerbox.presentation.listeners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 *  Custom [OnScrollListener] implementation to manage adapter's pagination
 *
 *  We provide a listener to invoke when last visible item is reached
 */
class PaginationScrollListener(
    private val adapter: Adapter<ViewHolder>?,
    private val layoutManager: LinearLayoutManager,
    private val onRefresh: (Int) -> Unit
): OnScrollListener() {

    private var pageNumber: Int = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lastVisibleItemPosition =
            layoutManager.findLastVisibleItemPosition()
        if(lastVisibleItemPosition == adapter?.itemCount?.minus(1)) {
            pageNumber++
            onRefresh.invoke(pageNumber)
        }
    }
}