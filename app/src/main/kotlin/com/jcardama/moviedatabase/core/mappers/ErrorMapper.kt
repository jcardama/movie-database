package com.jcardama.moviedatabase.core.mappers

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jcardama.moviedatabase.core.usecase.ErrorResponse
import com.jcardama.moviedatabase.core.usecase.ErrorStatus
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorMapper @Inject constructor() {
	fun mapToDomainErrorException(throwable: Throwable?): ErrorResponse {
		return when (throwable) {
			is HttpException -> {
				getHttpError(throwable.response()?.errorBody(), throwable.code(), when (throwable.code()) {
					400 -> ErrorStatus.BAD_REQUEST
					401 -> ErrorStatus.UNAUTHORIZED
					403 -> ErrorStatus.FORBIDDEN
					404 -> ErrorStatus.NOT_FOUND
					500 -> ErrorStatus.SERVER_ERROR
					502 -> ErrorStatus.NOT_AVAILABLE
					else -> ErrorStatus.NOT_DEFINED
				})
			}
			is SocketTimeoutException -> {
                ErrorResponse("TIME OUT!!", 0, ErrorStatus.TIMEOUT)
			}
			is IOException -> {
                ErrorResponse("CHECK CONNECTION", 0, ErrorStatus.NO_CONNECTION)
			}
			is UnknownHostException -> {
                ErrorResponse("CHECK CONNECTION", 0, ErrorStatus.NO_CONNECTION)
			}
			else -> ErrorResponse("UNKNOWN", 0, ErrorStatus.NOT_DEFINED)
		}
	}

	/**
	 * attempts to parse http response body and getPending mError data from it
	 *
	 * @param body retrofit response body
	 * @return returns an instance of [ErrorResponse] with parsed data or NOT_DEFINED status
	 */
	private fun getHttpError(body: ResponseBody?, code: Int, erroStatus: ErrorStatus): ErrorResponse {
		return try {
			when (code) {
				502 -> ErrorResponse(ErrorStatus.NOT_AVAILABLE)
				else -> ErrorResponse(Gson().fromJson(body?.string(), JsonObject::class.java).toString(), code, erroStatus)
			}
		} catch (e: Throwable) {
			e.printStackTrace()
            ErrorResponse(ErrorStatus.NOT_DEFINED)
		}

	}
}
