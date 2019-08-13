package com.jcardama.moviedatabase.data.mapper

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jcardama.moviedatabase.domain.model.response.ErrorModel
import com.jcardama.moviedatabase.domain.model.response.ErrorStatus
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorMapper @Inject constructor() {
	fun mapToDomainErrorException(throwable: Throwable?): ErrorModel {
		return when (throwable) {
			is HttpException -> {
				getHttpError(throwable.response()?.errorBody(), throwable.code(),
				             when (throwable.code()) {
					             400 -> ErrorStatus.BAD_REQUEST
					             401 -> ErrorStatus.UNAUTHORIZED
					             403 -> ErrorStatus.FORBIDDEN
					             404 -> ErrorStatus.NOT_FOUND
					             500 -> ErrorStatus.SERVER_ERROR
					             503 -> ErrorStatus.SERVICE_UNAVAILABLE
					             else -> ErrorStatus.NOT_DEFINED
				             })
			}
			is SocketTimeoutException -> {
				ErrorModel("TIME OUT!!", 0, ErrorStatus.TIMEOUT)
			}
			is IOException -> {
				ErrorModel("CHECK CONNECTION", 0, ErrorStatus.NO_CONNECTION)
			}
			is UnknownHostException -> {
				ErrorModel("CHECK CONNECTION", 0, ErrorStatus.NO_CONNECTION)
			}
			else -> ErrorModel("UNKNOWN", 0, ErrorStatus.NOT_DEFINED)
		}
	}

	/**
	 * attempts to parse http response body and getPending error data from it
	 *
	 * @param body retrofit response body
	 * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
	 */
	private fun getHttpError(body: ResponseBody?, code: Int, erroStatus: ErrorStatus): ErrorModel {
		return try {
			val json = Gson().fromJson(body?.string(), JsonObject::class.java)
			Timber.i("[DATA] json: $json")
			ErrorModel(json.toString(), code, erroStatus)
		} catch (e: Throwable) {
			e.printStackTrace()
			ErrorModel(ErrorStatus.NOT_DEFINED)
		}

	}
}
