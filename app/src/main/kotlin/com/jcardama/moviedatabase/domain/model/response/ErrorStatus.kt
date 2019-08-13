package com.jcardama.moviedatabase.domain.model.response

/**
 * various error status to know what happened if something goes wrong with a repository call
 */
enum class ErrorStatus {
    NO_CONNECTION,
    BAD_RESPONSE,
    TIMEOUT,
    EMPTY_RESPONSE,
    NOT_DEFINED,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    SERVER_ERROR,
    SERVICE_UNAVAILABLE
}
