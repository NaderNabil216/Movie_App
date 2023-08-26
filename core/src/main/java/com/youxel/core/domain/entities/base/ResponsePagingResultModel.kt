package com.youxel.core.domain.entities.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nader Nabil on 29/10/2020.
 */
data class ResponsePagingResultModel<T>(
    @Expose
    @SerializedName("data")
    val data: List<T>,
    @Expose
    @SerializedName("totalCount")
    val totalCount: Int,
    @Expose
    @SerializedName("pagesCount")
    val pagesCount: Int,

    var isInitialState: Boolean = false
)