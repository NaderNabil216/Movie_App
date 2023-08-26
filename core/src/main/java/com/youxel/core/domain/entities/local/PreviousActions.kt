package com.youxel.core.domain.entities.local

data class PreviousActions (
    var parentData: ParentData = ParentData(),
    var childData: ChildData = ChildData()
)