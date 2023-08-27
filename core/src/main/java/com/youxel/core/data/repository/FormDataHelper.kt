package com.youxel.core.data.repository

import okhttp3.Headers
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created By Nader Nabil.
 */
class FormDataHelper {

    companion object {
        @JvmStatic
        fun createFormData(
            name: String,
            filename: String?,
            body: RequestBody,
            fileMimeType: String
        ): MultipartBody.Part {
            val disposition = buildString {
                append("form-data; name=")
                appendQuotedString(name)

                append("; type=")
                appendQuotedString(fileMimeType)

                if (filename != null) {
                    append("; filename=")
                    appendQuotedString(filename)
                }
            }

            val headers =
                Headers.Builder().addUnsafeNonAscii("Content-Disposition", disposition).build()
            return MultipartBody.Part.create(headers, body)
        }

        fun StringBuilder.appendQuotedString(key: String) {
            append('"')
            for (i in 0 until key.length) {
                when (val ch = key[i]) {
                    '\n' -> append("%0A")
                    '\r' -> append("%0D")
                    '"' -> append("%22")
                    else -> append(ch)
                }
            }
            append('"')
        }
    }
}
