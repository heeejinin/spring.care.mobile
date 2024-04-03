package com.example.elderlycare.board.service

import com.example.elderlycare.board.vo.BoardVO
import retrofit2.Call
import retrofit2.http.GET

interface BoardService {
    @GET("boardlist")
    fun boardList() : Call<List<BoardVO>>

    @GET("csrf-token")
    fun getCsrfToken(): Call<Map<String, String>>
}