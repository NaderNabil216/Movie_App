package com.youxel.core.utils

import com.youxel.core.domain.entities.FileUploadQuery
import java.io.File

/**
 *  Created by Shehab Elsarky.
 */

fun getFileUploadQuery(filePath: String): FileUploadQuery {
    val fileUploadQuery = FileUploadQuery()
    val file = arrayListOf<File>()
    file.add(File(filePath))
    fileUploadQuery.apply {
        this.file = file
    }
    return fileUploadQuery
}

fun getFileUploadQuery(filePath: ArrayList<String>): FileUploadQuery {
    val fileUploadQuery = FileUploadQuery()
    val files = arrayListOf<File>()
    for (i in 0 until filePath.size) {
        val file = File(filePath[i])
        files.add(file)
    }
    fileUploadQuery.apply {
        this.file = files
    }
    return fileUploadQuery
}
