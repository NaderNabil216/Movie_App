package com.youxel.core.domain.entities.base

sealed class RelativeDateTime {
    override fun equals(other: Any?): Boolean {
        return if (other is com.youxel.core.domain.entities.base.FewSeconds && this is com.youxel.core.domain.entities.base.FewSeconds) true
        else if (other is com.youxel.core.domain.entities.base.Today && this is com.youxel.core.domain.entities.base.Today && other.date == this.date) true
        else if (other is com.youxel.core.domain.entities.base.RangeWeek && this is com.youxel.core.domain.entities.base.RangeWeek && other.numOfDays == this.numOfDays) true
        else other is com.youxel.core.domain.entities.base.MoreThanWeek && this is com.youxel.core.domain.entities.base.MoreThanWeek && other.date == this.date
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

object FewSeconds : com.youxel.core.domain.entities.base.RelativeDateTime()

class Today(val date: String) : com.youxel.core.domain.entities.base.RelativeDateTime()

class RangeWeek(val numOfDays: Int) : com.youxel.core.domain.entities.base.RelativeDateTime()

class MoreThanWeek(val date: String) : com.youxel.core.domain.entities.base.RelativeDateTime()