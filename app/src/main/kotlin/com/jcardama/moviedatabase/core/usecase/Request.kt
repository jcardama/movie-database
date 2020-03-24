package com.jcardama.moviedatabase.core.usecase

import java.util.concurrent.CancellationException

class Request<T> {
    private var onLoading: ((T) -> Unit)? = null
    private var onComplete: ((T) -> Unit)? = null
    private var onError: ((ErrorResponse) -> Unit)? = null
    private var onCancel: ((CancellationException) -> Unit)? = null

    fun onLoading(block: (T) -> Unit) {
        onLoading = block
    }

    fun onComplete(block: (T) -> Unit) {
        onComplete = block
    }

    fun onError(block: (ErrorResponse) -> Unit) {
        onError = block
    }

    fun onCancel(block: (CancellationException) -> Unit) {
        onCancel = block
    }

    operator fun invoke(result: T) {
        onComplete?.invoke(result)
    }

    operator fun invoke(error: ErrorResponse) {
        onError?.invoke(error)
    }

    operator fun invoke(error: CancellationException) {
        onCancel?.invoke(error)
    }
}
