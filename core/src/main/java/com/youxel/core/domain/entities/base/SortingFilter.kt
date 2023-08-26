package com.youxel.core.domain.entities.base

data class SortingFilter(
    var lastSelectedFilterIndexes: List<Int> = listOf(0),
    var resFiltersList: Array<String> = emptyArray(),
    var remoteKeysMap: MutableMap<Int, Any> = mutableMapOf()
) {
    constructor(
        lastSelectedFilterIndexes: List<Int>,
        resFiltersList: Array<String>,
        remoteKeyMap: Any
    ) : this()
    {
        this.lastSelectedFilterIndexes = lastSelectedFilterIndexes
        this.resFiltersList = resFiltersList
        this.remoteKeysMap = remoteKeyMap as MutableMap<Int, Any>
    }
}
