package com.youxel.core.domain.entities

import java.io.File

/**
 * Created by Shehab Elsarky.
 */
data class FileUploadQuery(
    var file: ArrayList<File> = arrayListOf(),
    var storageType: Int = 0,
    var isPublic: Boolean = false,
    var fileMimeType: String = ""
)