package com.example.myapplication.data

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}