package com.youxel.core.data.repository

import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Created By Nader Nabil.
 */
class AppRepositoryHelper {
    companion object {
        fun prepareFilePart(
            partName: String, file: File, fileMimeType: String
        ): MultipartBody.Part {
            val requestFile: RequestBody = file.asRequestBody(fileMimeType.toMediaTypeOrNull())
            return FormDataHelper.createFormData(partName, file.name, requestFile, fileMimeType)
        }

        fun getFileMimeType(file: File): String {
            val mimeMap: MimeTypeMap = MimeTypeMap.getSingleton()
            return mimeMap.getMimeTypeFromExtension(file.extension) ?: ""
        }
    }
}