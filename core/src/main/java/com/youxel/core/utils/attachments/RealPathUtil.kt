package com.youxel.core.utils.attachments

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.math.min


/**
 * Real Path Utility class for Android.
 *
 * Updated by @ImaginativeShohag
 *
 * Source: https://gist.github.com/ImaginativeShohag/4e53572141c017369941bbbaac538576
 */
class RealPathUtil {
    companion object {

        fun getRealPathFromURI(
            context: Context,
            uri: Uri
        ): String? {
            val returnCursor = context.contentResolver.query(uri, null, null, null, null)
            val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
            returnCursor.moveToFirst()
            val name = returnCursor.getString(nameIndex)
            val size = java.lang.Long.toString(returnCursor.getLong(sizeIndex))
            val file = File(context.filesDir, name)
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(file)
                var read = 0
                val maxBufferSize = 1 * 1024 * 1024
                val bytesAvailable: Int = inputStream!!.available()

                //int bufferSize = 1024;
                val bufferSize = min(bytesAvailable, maxBufferSize)
                val buffers = ByteArray(bufferSize)
                while (inputStream.read(buffers).also { read = it } != -1) {
                    outputStream.write(buffers, 0, read)
                }
                Log.d("File Size", "Size " + file.length())
                inputStream.close()
                outputStream.close()
                Log.d("File Path", "Path " + file.path)
                Log.d("File Size", "Size " + file.length())
            } catch (e: java.lang.Exception) {
                Log.d("Exception", e.message!!)
            }
            return file.path
        }
    }
}