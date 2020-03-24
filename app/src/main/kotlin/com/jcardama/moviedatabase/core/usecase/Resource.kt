package com.jcardama.moviedatabase.core.usecase

import java.util.concurrent.CancellationException

data class Resource<I, O>(var state: State?, var request: I? = null, var data: O? = null, val error: ErrorResponse? = null, val exception: CancellationException? = null) {
    companion object {
        fun <I, O> loading(data: I?): Resource<I, O> {
            return Resource(state = State.LOADING, request = data)
        }

        fun <I, O> success(data: O?): Resource<I, O> {
            return Resource(state = State.SUCCESS, data = data)
        }

        fun <I, O> cancel(ex: CancellationException): Resource<I, O> {
            return Resource(state = State.CANCELLED, exception = ex)
        }

        fun <I, O> error(error: ErrorResponse): Resource<I, O> {
            return Resource(state = State.ERROR, error = error)
        }
    }
}
