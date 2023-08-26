package com.youxel.core.domain.entities.local

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Shehab Elsarky.
 */
data class ChildData(
    var department: String = "",
    var date: String = "",
    var comment: String = ""
) : Parcelable {

    constructor(`in`: Parcel) : this(`in`.readString()!!,`in`.readString()!!,`in`.readString()!!)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(department)
        dest.writeString(date)
        dest.writeString(comment)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ChildData?> = object : Parcelable.Creator<ChildData?> {
            override fun createFromParcel(`in`: Parcel): ChildData? {
                return ChildData(`in`)
            }

            override fun newArray(size: Int): Array<ChildData?> {
                return arrayOfNulls(size)
            }
        }
    }
}