@file:Suppress("unused", "UNCHECKED_CAST")

package com.jcardama.moviedatabase.domain.usecase.base

import com.jcardama.moviedatabase.data.mapper.ErrorMapper
import com.jcardama.moviedatabase.domain.model.response.ErrorModel
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<in I: Any?, O>(private val errorUtil: ErrorMapper) {

    private var parentJob: Job = Job()

    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(data: I): O

    fun execute(data: I = Any() as I, block: CompletionBlock<O>) {
        val response = Request<O>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                response(withContext(backgroundContext) {
                    executeOnBackground(data)
                })
            } catch (cancellationException: CancellationException) {
                Timber.e(cancellationException)
                response(cancellationException)
            } catch (e: Exception) {
                Timber.e(e)
                response(errorUtil.mapToDomainErrorException(e))
            }
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((ErrorModel) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (ErrorModel) -> Unit) {
            onError = block
        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }


        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: ErrorModel) {
            onError?.invoke(error)
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.invoke(error)
        }
    }
}
