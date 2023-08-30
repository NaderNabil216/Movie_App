package com.youxel.core.base.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.youxel.core.R
import com.youxel.core.base.adapter.diffutilsAdapter.BaseRecyclerAdapter
import com.youxel.core.base.view_model.ApiState
import com.youxel.core.base.view_model.BasePagingViewModel
import com.youxel.core.domain.entities.base.ResponsePagingResultModel
import com.youxel.core.utils.EndlessRecyclerViewScrollListener
import com.youxel.core.utils.getVerticalLayoutManager
import com.youxel.core.utils.hideKeyBoardOutSideTap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BasePagingFragment<T : Any, VBI : ViewBinding, VB : ViewBinding, VM, HelperClass : BaseUiHelper>(
    inflate: Inflate<VB>
) : BaseFragment<VB, VM, HelperClass>(inflate),
    SwipeRefreshLayout.OnRefreshListener where VM : BasePagingViewModel<T> {

    abstract val pagingAdapter: BaseRecyclerAdapter<VBI, T>

    /*
    all paging fragment layout must include @layout/paging_view, just include and forget it.

    OR if u want to use custom layout for paging with different ids use methods to provide views.
     */

    open fun getPagingRecycler(): RecyclerView = requireView().findViewById(R.id.rvPaging)
        ?: throw Throwable("Can't find view with id rvPaging, Include paging_view or override getPagingRecycler")

    open fun getSwipeToRefreshView(): SwipeRefreshLayout =
        requireView().findViewById(R.id.swipeContainer)
            ?: throw Throwable("Can't find view with id swipeContainer, Include paging_view or override getSwipeToRefreshView")

    open fun getLoadMoreIndicator(): View = requireView().findViewById(R.id.loadMore)
        ?: throw Throwable("Can't find view with id loadMore, Include paging_view or override getLoadMoreIndicator")

    open fun getEmptyView(): View = requireView().findViewById(R.id.emptyView)
        ?: throw Throwable("Can't find view with id emptyView, Include paging_view or override getEmptyView")

    open fun getEmptyViewText(): TextView = requireView().findViewById(R.id.tv_empty_list)
        ?: throw Throwable("Can't find view with id tv_empty_list, Include paging_view or override getEmptyViewText")

    open fun getEmptyViewSubtitleText(): TextView =
        requireView().findViewById(R.id.tv_empty_list_subtitle)
            ?: throw Throwable("Can't find view with id tv_empty_list_subtitle, Include paging_view or override getEmptyViewText")

    open fun getEmptyViewImg(): ImageView = requireView().findViewById(R.id.img_empty_state)
        ?: throw Throwable("Can't find view with id img_empty_state, Include paging_view or override getEmptyViewImg")

    open fun getSortViewImage(): ImageView = requireView().findViewById(R.id.ivSort)
        ?: throw Throwable("Can't find view with id ivSort, Include paging_view or override getSortViewImage")

    open fun getSortImageViewEnabled(): ImageView = requireView().findViewById(R.id.ivSortEnabled)
        ?: throw Throwable("Can't find view with id ivSortEnabled, Include paging_view or override getSortImageViewEnabled")

    open fun getPagingHeaderTitleTextView(): TextView =
        requireView().findViewById(R.id.tvPagingHeaderTitle)
            ?: throw Throwable("Can't find view with id tvPagingHeaderTitle, Include paging_view or override getPagingHeaderTitleTextView")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initEmptyView()
        lifecycleScope.launchWhenCreated {
            viewModel.pagingList.asLiveData().observe(viewLifecycleOwner) {
                showOrHideLoadMoreView(false)
                cancelSwipeToRefresh()
                when (it) {
                    is ApiState.Idle -> {}
                    is ApiState.Success -> {
                        initShowingTitle(it.successData.totalResults.toString())
                        showOrHideEmptyView(it.successData.totalResults == 0)
                        pagingResponseObserver(it.successData)
                    }
                }
            }
        }

        getSwipeToRefreshView().setOnRefreshListener(this)
        viewModel.initPageNumberAndFetchPage()

        getSortViewImage().setOnClickListener {
            onSortIconClick()
        }
    }

    open fun initRecycler() {

        getPagingRecycler().run {
            this.hideKeyBoardOutSideTap()
            setHasFixedSize(true)
            adapter = pagingAdapter

            if (layoutManager == null)
                layoutManager = getVerticalLayoutManager(requireContext())
        }
        initRecyclerLoadMore()
    }

    private fun initRecyclerLoadMore() {
        /** Loading More Condition**/
        getPagingRecycler().addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(getPagingRecycler().layoutManager!! as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (viewModel.loadMore) {
                    viewModel.incrementPageNumberAndFetchPage()
                    showOrHideLoadMoreView(viewModel.pageNumber > 1)
                }
            }
        })
    }

    open fun pagingResponseObserver(response: ResponsePagingResultModel<T>) {
        if (viewModel.pageNumber == 1) {
            pagingAdapter.submitList(response.data)
        } else if (viewModel.pageNumber > 1) {
            pagingAdapter.addToList(response.data)
        }
        viewModel.loadMore = pagingAdapter.itemCount < response.totalResults
    }

    open fun onSortIconClick() {}


    override fun onViewModelError() {
        super.onViewModelError()
        cancelSwipeToRefresh()
        showOrHideLoadMoreView(false)
    }


    fun showOrHideLoadMoreView(isVisible: Boolean) {
        getLoadMoreIndicator().visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun cancelSwipeToRefresh() {
        if (getSwipeToRefreshView().isRefreshing)
            getSwipeToRefreshView().isRefreshing = false
    }

    override fun onRefresh() {
        getSwipeToRefreshView().isRefreshing = true
        getLoadMoreIndicator().visibility = View.GONE
        pagingAdapter.clear()
        viewModel.resetPageNumberAndFetchPage()
    }


    open fun showOrHideEmptyView(isEmptyStateVisible: Boolean) {
        if (viewModel.pageNumber == 1) {
            getPagingRecycler().isVisible = isEmptyStateVisible.not()
            getEmptyView().isVisible = isEmptyStateVisible
        }
    }

    private fun initEmptyView() {
        setEmptyViewTxt(emptyViewTxtRes)
        setEmptyViewSubtitleTxt(emptyViewSubtitleTxtRes)
        setEmptyViewIcon(emptyViewIcon)
    }

    open val emptyViewIcon = R.drawable.ic_general_empty_view

    open val emptyViewSubtitleTxtRes = R.string.empty_title

    abstract val emptyViewTxtRes: Int

    fun setEmptyViewTxt(@StringRes str: Int) {
        getEmptyViewText().setText(str)
    }

    fun setEmptyViewSubtitleTxt(@StringRes str: Int) {
        getEmptyViewSubtitleText().setText(str)
    }

    fun setEmptyViewIcon(@DrawableRes icon: Int) {
        getEmptyViewImg().setImageResource(icon)
    }

    private fun initShowingTitle(listSize: String) {
        getPagingHeaderTitleTextView().text =
            getString(R.string.showing, listSize)
    }

    fun toggleSortRedDot(isVisible: Boolean = false) {
        getSortImageViewEnabled().isVisible = isVisible
    }

    open fun onSearchQueryReady(searchQuery: String) {}

    open fun clearSearchResults() {}

    open fun setSearchKeyword(searchQuery: String) {}

    open fun initSearchView() {}
}