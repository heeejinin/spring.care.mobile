package com.example.elderlycare.notice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.elderlycare.R

class NoticeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_notice_layout)

        // Intent에서 전달된 공지사항 ID를 가져옵니다.
        val noticeId = intent.getIntExtra("notice_id", -1)

        // 가져온 ID를 사용하여 해당 공지사항의 상세 정보를 표시합니다.
        // 상세 정보를 표시하는 코드 작성
    }
}