package com.youxel.core.domain.entities

import java.io.File

/**
 * Created By Nader Nabil.
 */
data class FileUploadQuery(
    var file: ArrayList<File> = arrayListOf(),
    var storageType: Int = 0,
    var isPublic: Boolean = false,
    var fileMimeType: String = ""
)