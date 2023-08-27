package com.youxel.core.base.viewState

/**
 * Created by Nader Nabil on 21/12/15.
 */
sealed class
BaseViewState {
    object Idle : BaseViewState()
    object Loading : BaseViewState()
    data class Error(val error: String?) : BaseViewState()
    data class OnSuccess(val data: Any) : BaseViewState()

}
