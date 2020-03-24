package com.jcardama.moviedatabase.domain.repository

interface Repository {
    /**
     * A generic class that can provide a resource backed by both the sqlite database and the network.
     * @param <ResultType>
     * @param <RequestType>
    </RequestType></ResultType> */
    abstract class NetworkBound<ResultType, RequestType> {
        suspend fun call(): ResultType? {
            val dbSource = localCall()
            return mapToDomain(when {
                shouldFetch(dbSource) -> fetchFromNetwork()
                else -> dbSource
            })
        }

        private suspend fun fetchFromNetwork(): RequestType? {
            saveCallResult(networkCall())
            return localCall()
        }

        protected abstract suspend fun saveCallResult(item: RequestType?)
        protected abstract fun shouldFetch(data: RequestType?): Boolean
        protected abstract suspend fun localCall(): RequestType?
        protected abstract suspend fun networkCall(): RequestType?
        protected abstract suspend fun mapToDomain(data: RequestType?): ResultType?
    }
}
