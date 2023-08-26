package com.youxel.core.domain.entities.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


abstract class BaseResponse<T> {

    @SerializedName("success")
    @Expose
    val success: Boolean = false

    @SerializedName("errorCode")
    @Expose
    val errorCode: Int = 0

    @SerializedName("message")
    @Expose
    val message: String = ""

    @SerializedName("result")
    @Expose
    val result: ResponseResultModel<T>? = null

    var isInitialState: Boolean = false

}