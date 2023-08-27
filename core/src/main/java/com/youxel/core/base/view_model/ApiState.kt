package com.youxel.core.base.view_model


/**
 * Created by Nader Nabil on 16/12/2021.
 */
sealed class ApiState<out T>
{
    object Idle : ApiState<Nothing>()
    data class Success<T>(val successData: T) : ApiState<T>()

}
