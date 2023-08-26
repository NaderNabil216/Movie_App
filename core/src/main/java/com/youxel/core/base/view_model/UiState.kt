package com.youxel.core.base.view_model

import com.youxel.core.domain.entities.base.ErrorModel

/**
 * Created by ahmed abdelsaimee on 16/12/2021.
 */

sealed class UiState {
    object Idle : UiState()
    data class Loading(val Loading: Boolean) : UiState()
    data class Error(val Error: ErrorModel) : UiState()
    data class CancellationMessage(val CancellationMessage: String) : UiState()

}
