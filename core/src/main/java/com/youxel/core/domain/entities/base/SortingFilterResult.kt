package com.youxel.core.domain.entities.base

import java.io.Serializable

/**
 * Created by Mohamed Assem Ali on 26 Jul 2022.
 */
data class SortingFilterResult(
    var id: Int = 0,
    var title: String = "",
    var remoteKey: Any? = null,
) : Serializable
