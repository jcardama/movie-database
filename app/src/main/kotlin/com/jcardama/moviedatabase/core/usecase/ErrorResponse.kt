package com.jcardama.moviedatabase.core.usecase

/**
 * Default mError model that comes from server if something goes wrong with a repository call
 */
data class ErrorResponse(
    val message: String?,
    val code: Int?,
    @Transient var errorStatus: ErrorStatus
) {
    constructor(errorStatus: ErrorStatus) : this(null, null, errorStatus)
}
