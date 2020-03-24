@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.jcardama.moviedatabase.core.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jcardama.moviedatabase.core.mappers.ErrorMapper
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = Request<T>.() -> Unit

abstract class UseCase<I, O>(private val errorUtil: ErrorMapper) {

    private val result = MediatorLiveData<Resource<I, O>>()

    private var parentJob: Job = Job()

    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(params: I): O

    @Suppress("UNCHECKED_CAST")
    fun executeLive(params: I = Any() as I): LiveData<Resource<I, O>> {
        result.value = Resource.loading(params)

        parentJob = Job()

        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                result.value = Resource.success(withContext(backgroundContext) {
                    executeOnBackground(params)
                })
            } catch (cancellationException: CancellationException) {
                Timber.e(cancellationException)
                result.value = Resource.cancel(cancellationException)
            } catch (e: Exception) {
                Timber.e(e)
                result.value = Resource.error(errorUtil.mapToDomainErrorException(e))
            }
        }

        return result
    }

    @Suppress("UNCHECKED_CAST")
    fun execute(params: I = Any() as I, block: CompletionBlock<O>) {
        val response = Request<O>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                response(withContext(backgroundContext) {
                    executeOnBackground(params)
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
}
