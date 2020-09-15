package com.picpay.desafio.android.commom.data

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.picpay.desafio.android.commom.data.model.Error
import com.picpay.desafio.android.commom.data.model.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException

abstract class NetworkBoundResource<RequestType, ResultType> {

    private val result = MediatorLiveData<State<ResultType>>()
    private val dbSource by lazy {
        loadFromDb()
    }

    init {
        val context = Dispatchers.IO

        result.value = State.Loading(null)
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            if (shouldFetch(it)) {
                CoroutineScope(context).launch {
                    fetchFromNetwork(dbSource)
                }
            } else {
                result.addSource(dbSource) { response ->
                    setValue(State.Success(response))
                }
            }
        }
    }

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        withContext(Dispatchers.Main.immediate) {
            result.addSource(dbSource) {
                setValue(State.Loading(it))
            }
        }
        try {
            val apiResponse = makeApiCall()
            saveCallResult(apiResponse)
            withContext(Dispatchers.Main.immediate) {
                result.removeSource(dbSource)
                result.addSource(dbSource) {
                    setValue(State.Success(it))
                }
            }
        } catch (exception: Exception) {
            val error = handleError(exception)
            withContext(Dispatchers.Main.immediate) {
                result.removeSource(dbSource)
                result.addSource(dbSource) {
                    setValue(State.Failure(error, it))
                }
            }
        }
    }

    private fun handleError(exception: Exception): Error {
        return when (exception) {
            is HttpException -> {
                Error.FeatureError(exception)
            }
            is SocketTimeoutException -> {
                Error.NetworkError
            }
            else -> Error.ServerError(exception)
        }
    }

    @MainThread
    private fun setValue(newValue: State<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @WorkerThread
    protected abstract suspend fun makeApiCall(): RequestType

    @WorkerThread
    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asLiveData() = result as LiveData<State<ResultType>>
}