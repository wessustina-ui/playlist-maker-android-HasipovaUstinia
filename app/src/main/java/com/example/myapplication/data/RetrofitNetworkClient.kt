package com.example.myapplication.data

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(request: Any): TracksSearchResponse {
        val searchList = storage.search((request as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}