package com.example.elderlycare.mypage.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import com.example.elderlycare.MainActivity
import com.example.elderlycare.R
import com.example.elderlycare.databinding.ActivitySeniorMypageBinding
import com.example.elderlycare.ui.NavItem1Activity
import com.example.elderlycare.ui.NavItem2Activity
import com.example.elderlycare.ui.NavItem3Activity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class SeniorMypageActivity : AppCompatActivity() {
    private lateinit var navigationView: NavigationView
    private lateinit var navViewContainer: FrameLayout
    private lateinit var binding: ActivitySeniorMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senior_mypage)
        binding = ActivitySeniorMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //// 네비게이션

        navigationView = findViewById(R.id.nav_view)
        navViewContainer = findViewById(R.id.nav_view_container)

        // 헤더 레이아웃에서 버튼과 네비게이션 뷰 컨테이너 가져오기
        val headerLayout = findViewById<RelativeLayout>(R.id.header_layout)
        val btnMenu = headerLayout.findViewById<ImageButton>(R.id.btnMenu)
        navViewContainer = headerLayout.findViewById(R.id.nav_view_container)

        // 네비게이션 뷰 초기화
        navigationView = findViewById(R.id.nav_view)

        // NavigationView 메뉴 아이템 선택 이벤트 처리
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    // nav_item1 선택 시 처리
                    startActivity(Intent(this, NavItem1Activity::class.java))
                }
                R.id.nav_item2 -> {
                    // nav_item2 선택 시 처리
                    startActivity(Intent(this, NavItem2Activity::class.java))
                }
                R.id.nav_item3 -> {
                    // nav_item3 선택 시 처리
                    startActivity(Intent(this, NavItem3Activity::class.java))
                }
                // 다른 메뉴 아이템에 대한 처리
            }
            true// true 반환하여 클릭 이벤트 소비
        }

        // 메뉴 버튼 클릭 이벤트 처리
        btnMenu.setOnClickListener {
            toggleNavViewVisibility()
            // 네비게이션 뷰를 페이지 맨 위로 이동
            moveNavViewToTop()
        }
        // ImageView 클릭 이벤트 처리
        val imageViewLogo = findViewById<ImageView>(R.id.logo)
        imageViewLogo.setOnClickListener {
            // MainActivity로 돌아가는 Intent 생성
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }   //보완필요

        val tabLayout = binding.tabs

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                Log.d(">>", tab?.text.toString())
                when (tab?.text) {
                    "나의 정보" -> transaction.replace(R.id.tabContent, MyInfoFragment())
                    "매칭 정보" -> transaction.replace(R.id.tabContent, MatchingInfoFragment())
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                Log.d(">>", tab?.text.toString())
                when (tab?.text) {
                    "나의 정보" -> transaction.replace(R.id.tabContent, MyInfoFragment())
                    "매칭 정보" -> transaction.replace(R.id.tabContent, MatchingInfoFragment())
                }
                transaction.commit()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                Log.d(">>", tab?.text.toString())
                when (tab?.text) {
                    "나의 정보" -> transaction.replace(R.id.tabContent, MyInfoFragment())
                    "매칭 정보" -> transaction.replace(R.id.tabContent, MatchingInfoFragment())
                }
                transaction.commit()
            }
        })
        supportFragmentManager.beginTransaction().add(R.id.tabContent, MyInfoFragment()).commit()







    }
    private fun toggleNavViewVisibility() {
        val navViewContainer = findViewById<FrameLayout>(R.id.nav_view_container)
        if (navViewContainer.visibility == View.VISIBLE) {
            // 네비게이션 뷰가 보이는 경우, 유지
            navViewContainer.visibility = View.GONE
        } else {
            // 네비게이션 뷰가 숨겨진 경우, 보이게 함
            navViewContainer.visibility = View.VISIBLE
        }
    }
    private fun moveNavViewToTop() {
        // 네비게이션 뷰를 페이지 맨 위로 이동
        ViewCompat.offsetTopAndBottom(navViewContainer, -navViewContainer.top)
    }





}