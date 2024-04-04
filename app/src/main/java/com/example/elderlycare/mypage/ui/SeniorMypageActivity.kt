package com.example.elderlycare.mypage.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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

        val tabLayout = binding.tabs

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

        setSupportActionBar(findViewById(R.id.toolbar))
        val frgMng: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = frgMng.beginTransaction()
        val myInfoFrg: MyInfoFragment = MyInfoFragment()
        transaction.add(R.id.tabContent, myInfoFrg)
        transaction.commit()
//    twoFrg.setTextV("hello hello")
//        binding.button.setOnClickListener {
//            val xx = binding.layoutForTwofrg?.get(R.id.frgtwotxt) as TextView
//            Log.d(">>", xx.toString())
////      val frg2txt = frgMng?.findFragmentById(R.id.frgtwotxt) as TextView
////      frg2txt.text = ">> hello hello"
//        }
    }
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            setTitle("")
            menuInflater.inflate(R.menu.mypage_bar_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu1 -> {}
                R.id.menu2 -> {}
                R.id.menu3 -> {}
                else -> {}
            }
            return super.onOptionsItemSelected(item)
        }













}