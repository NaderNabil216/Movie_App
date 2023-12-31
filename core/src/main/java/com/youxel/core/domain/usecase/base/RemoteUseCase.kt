package com.youxel.core.domain.usecase.base

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.youxel.core.base.view_model.ApiState
import com.youxel.core.data.mapper.CloudErrorMapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = RemoteUseCase.Request<T>.() -> Unit


/**
 * Created By Nader Nabil
 */
/**
 * @type in Query paramater
 * @type RemoteResponse DTO result
 * @type LocalResponse(final result) mapped DTO to BO
 */
abstract class RemoteUseCase<Query, RemoteResponse, LocalResponse>(protected val errorUtil: CloudErrorMapper) {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(parameters: Query): RemoteResponse
    protected abstract suspend fun convert(dto: RemoteResponse): LocalResponse

    fun execute(parameters: Query, block: CompletionBlock<LocalResponse>) {
        val response = Request<LocalResponse>().apply { block() }
        CoroutineScope(foregroundContext + parentJob).launch {
            response(true)
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(parameters)
                }

                try {
                    val remoteResponseMessage = getMessageFromRemoteResponse(dto = result)

                    if (remoteResponseMessage.isNotEmpty()) {
                        response(errorUtil.mapToResponseErrorMessage(remoteResponseMessage))
                        response(false)
                        return@launch
                    } else
                        response(convert(result))
                    response(convert(result))

                } catch (e: JsonSyntaxException) {
                    e.printStackTrace()
                    response(convert(result))
                } catch (e: JSONException) {
                    e.printStackTrace()
                    response(convert(result))
                }

                response(false)
            } catch (cancellationException: CancellationException) {
                response(cancellationException)
                response(false)
            } catch (e: Exception) {
                e.printStackTrace()
                val error = errorUtil.mapToDomainErrorException(e)
                response(error)
                response(false)
            }
        }
    }

    fun executeWithApiState(parameters: Query, block: CompletionBlock<ApiState<LocalResponse>>) {
        val response = Request<ApiState<LocalResponse>>().apply { block() }
        CoroutineScope(foregroundContext + parentJob).launch {
            response(true)
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(parameters)
                }

                try {
                    val remoteResponseMessage = getMessageFromRemoteResponse(dto = result)

                    if (remoteResponseMessage.isNotEmpty()) {
                        response(errorUtil.mapToResponseErrorMessage(remoteResponseMessage))
                        response(ApiState.Success(convert(result)))
                        response(false)
                        return@launch
                    } else
                        response(ApiState.Success(convert(result)))

                } catch (e: JsonSyntaxException) {
                    e.printStackTrace()
                    response(ApiState.Success(convert(result)))
                } catch (e: JSONException) {
                    e.printStackTrace()
                    response(ApiState.Success(convert(result)))
                }

                response(false)
            } catch (cancellationException: CancellationException) {
                response(cancellationException)
                response(false)
            } catch (e: Exception) {
                e.printStackTrace()
                val error = errorUtil.mapToDomainErrorException(e)
                response(error)
                response(false)
            }
        }
    }

    private fun <RemoteResponse> getMessageFromRemoteResponse(dto: RemoteResponse): String {
        return if (Gson().toJson(dto).trim().startsWith("{")) {
            val response = JSONObject(Gson().toJson(dto))
            val status = response.optBoolean("success")
            if (!status) {
                response.optString("message").orEmpty()
            } else
                ""
        } else ""
    }

    private fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var isLoading: ((Boolean) -> Unit)? = null
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((com.youxel.core.domain.entities.base.ErrorModel) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null


        fun isLoading(isLoading: (Boolean) -> Unit) {

            this.isLoading = isLoading
        }

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (com.youxel.core.domain.entities.base.ErrorModel) -> Unit) {
            onError = block
        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }

        operator fun invoke(loading: Boolean) {
            isLoading?.invoke(loading)
        }

        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: com.youxel.core.domain.entities.base.ErrorModel) {
            onError?.invoke(error)
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.invoke(error)
        }

    }
}