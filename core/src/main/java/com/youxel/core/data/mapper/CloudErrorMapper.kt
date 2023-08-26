package com.youxel.core.data.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.youxel.core.domain.entities.base.ErrorModel
import com.youxel.core.domain.entities.base.ErrorStatus
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class CloudErrorMapper @Inject constructor(private val gson: Gson) {

    fun mapToDomainErrorException(throwable: Throwable?): ErrorModel {
        val errorModel: ErrorModel? = when (throwable) {

            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {
                // handle UNAUTHORIZED situation (when token expired)
                when {
                    throwable.code() == 401 -> ErrorModel(
                        ErrorStatus.UNAUTHORIZED,
                        401
                    )
                    throwable.code() == 500 -> ErrorModel(
                        ErrorStatus.INTERNAL_SERVER_ERROR,
                        500
                    )
                    throwable.code() == 403 -> ErrorModel(
                        ErrorStatus.FORRBIDEN,
                        403
                    )
                    throwable.code() == 404 -> ErrorModel(
                        ErrorStatus.NOT_FOUND,
                        404
                    )
                    throwable.code() == 400 -> getBadRequestHttpError(
                        throwable.response()?.errorBody()
                    )
                    else -> getHttpError(throwable.response()?.errorBody())
                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel(
                    "No internet connection!!",
                    0,
                    ErrorStatus.TIMEOUT
                )
            }

            // handle connection error
            is UnknownHostException -> {
                ErrorModel(
                    "Server error",
                    0,
                    ErrorStatus.UNKNOWN_HOST
                )
            }

            is IOException -> {
                ErrorModel(
                    "No internet connection",
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            }

            else ->
                ErrorModel(
                    "NOT DEFINED",
                    0,
                    ErrorStatus.NOT_DEFINED
                )
        }
        return errorModel!!
    }

    fun mapToResponseErrorMessage(message: String): ErrorModel {
        return ErrorModel(
            message,
            400,
            ErrorStatus.EMPTY_RESPONSE
        )
    }


    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            val result = body?.string()
            Log.d("getHttpError", "getErrorMessage() called with: errorBody = [$result]")
            val json = Gson().fromJson(result, JsonObject::class.java)
            ErrorModel(
                json["error_description"]?.asString ?: json.asString,
                400,
                ErrorStatus.BAD_RESPONSE
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(ErrorStatus.NOT_DEFINED)
        }
    }

    private fun getBadRequestHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            val result = body?.string()
            Log.d("getHttpError", "getErrorMessage() called with: errorBody = [$result]")
            val json = Gson().fromJson(result, JsonObject::class.java)
            ErrorModel(
                json["message"]?.asString ?: json.asString,
                400,
                ErrorStatus.BAD_RESPONSE
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(ErrorStatus.NOT_DEFINED)
        }
    }
}