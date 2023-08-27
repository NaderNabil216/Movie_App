package com.youxel.core.base.view_model

import com.youxel.core.domain.entities.base.ResponsePagingResultModel
import com.youxel.core.domain.usecase.base.CompletionBlock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Nader Nabil on 10/31/2021.
 */
@ExperimentalCoroutinesApi
abstract class BasePagingViewModel<T> : BaseViewModel() {

    var pageNumber = 0
    open var pageSize = 10
    var loadMore = false

    private val pagingListMutable =
        MutableStateFlow<ApiState<ResponsePagingResultModel<T>>>(ApiState.Idle)
    val pagingList: StateFlow<ApiState<ResponsePagingResultModel<T>>> = pagingListMutable

    open fun initPageNumberAndFetchPage() {
        if (pageNumber == 0) {
            pageNumber = 1
            callPagingApi()
        }
    }


    open fun resetPageNumberAndFetchPage() {
        pageNumber = 1
        loadMore = false
        callPagingApi()
        pagingListMutable.value = ApiState.Idle

    }

    fun incrementPageNumberAndFetchPage() {
        pageNumber++
        callPagingApi()
    }

    private fun callPagingApi() {
        callApiWithApiState(pagingListMutable) {
            fetchPage(it)
        }
    }

    abstract fun fetchPage(completionBlock: CompletionBlock<ApiState<ResponsePagingResultModel<T>>>)

}