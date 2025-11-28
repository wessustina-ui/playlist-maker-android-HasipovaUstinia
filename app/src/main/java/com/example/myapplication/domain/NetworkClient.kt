package com.example.myapplication.domain

import com.example.myapplication.data.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}