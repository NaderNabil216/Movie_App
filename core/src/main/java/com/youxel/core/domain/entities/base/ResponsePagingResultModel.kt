package com.youxel.core.domain.entities.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nader Nabil on 29/10/2020.
 */
data class ResponsePagingResultModel<T>(
    val data: List<T>,
    val totalResults: Int,
    val totalPages: Int
)