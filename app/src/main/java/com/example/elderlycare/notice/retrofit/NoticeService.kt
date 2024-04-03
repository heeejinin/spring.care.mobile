package com.example.elderlycare.notice.retrofit

import com.example.elderlycare.notice.model.NoticeResponse
import retrofit2.Call
import retrofit2.http.GET

interface NoticeService {
    @GET("/m/notices")
    fun getNotices(): Call<NoticeResponse>
}