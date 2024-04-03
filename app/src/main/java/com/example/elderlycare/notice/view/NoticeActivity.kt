package com.example.elderlycare.notice.view

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elderlycare.BaseActivity
import com.example.elderlycare.R
import com.example.elderlycare.notice.adapter.NoticeAdapter
import com.example.elderlycare.notice.model.NoticeResponse
import com.example.elderlycare.notice.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noticeAdapter: NoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        setupNavigationView() // 네비게이션 뷰 설정

        //listNotice(메인페이지 공지사항 텍스트 >> 공지사항 리스트)
        recyclerView = findViewById(R.id.listNotice)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // NoticeAdapter 초기화 및 연결
        noticeAdapter = NoticeAdapter(this, mutableListOf())
        recyclerView.adapter = noticeAdapter

        // 공지사항 데이터 로드
        loadNotices()


        // 공지사항 데이터 가져오기
        val noticeService = RetrofitClient.noticeService
        noticeService.getNotices().enqueue(object : Callback<NoticeResponse> { // Callback<NoticeResponse>로 수정
            override fun onResponse(call: Call<NoticeResponse>, response: Response<NoticeResponse>) { // Response<NoticeResponse>로 수정
                if (response.isSuccessful) {
                    val noticesResponse = response.body()
                    val notices = noticesResponse?.content ?: emptyList() // NoticeResponse에서 공지 목록 가져오기
                    val noticeAdapter = NoticeAdapter(this@NoticeActivity, notices)
                    recyclerView.adapter = noticeAdapter
                } else {
                    Log.e("Notice", "Failed to get notices: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<NoticeResponse>, t: Throwable) { // Callback<NoticeResponse>로 수정
                Log.e("Notice", "Error getting notices", t)
            }
        })
    }

    private fun loadNotices() {
        // NoticeAdapter에 정의한 loadNotices 함수 호출하여 데이터 로드
        noticeAdapter.loadNotices()
    }
}