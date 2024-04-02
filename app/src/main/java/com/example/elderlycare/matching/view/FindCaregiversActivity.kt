package com.example.elderlycare.matching.view

import android.os.Bundle
import android.widget.AbsListView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.elderlycare.R
import com.example.elderlycare.matching.adapter.CaregiversAdapter
import com.example.elderlycare.matching.model.Caregiver
import org.json.JSONException

class FindCaregiversActivity : AppCompatActivity() {

    private lateinit var caregiversListView: ListView
    private val caregiverList = mutableListOf<Caregiver>()
    private lateinit var caregiversAdapter: CaregiversAdapter

    private var currentPage = 0
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matching_activity_find_caregivers)

        caregiversListView = findViewById(R.id.caregivers_listview)
        caregiversAdapter = CaregiversAdapter(this, caregiverList)
        caregiversListView.adapter = caregiversAdapter

        fetchCaregivers(currentPage)

        caregiversListView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val lastVisibleItem = firstVisibleItem + visibleItemCount
                if (!isLoading && !isLastPage && lastVisibleItem == totalItemCount) {
                    currentPage++
                    fetchCaregivers(currentPage)
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}
        })
    }

    private fun fetchCaregivers(page: Int) {
        isLoading = true
        val url = "http://10.100.103.28/m/matching/caregivers?page=$page&field=&word="

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val contentArray = response.getJSONArray("content")
                    for (i in 0 until contentArray.length()) {

                        val caregiverObject = contentArray.getJSONObject(i)
                        val userObject = caregiverObject.getJSONObject("user")
                        val caregiverDetailsObject = caregiverObject.getJSONObject("caregiver")

                        val name = userObject.getString("name")
                        val country = userObject.getString("country")
                        val experience = caregiverDetailsObject.getString("experience")
                        val certification = caregiverDetailsObject.getString("certification")
                        val availableHours = caregiverDetailsObject.getString("availableHours")
                        val gender = userObject.getString("gender")
                        val image = userObject.getString("image")
                        val caregiverId = caregiverDetailsObject.getInt("caregiverId")

                        val caregiver = Caregiver(name, country, experience, certification, availableHours, image, gender, caregiverId)
                        caregiverList.add(caregiver)
                    }

                    isLoading = false
                    caregiversAdapter.notifyDataSetChanged()

                    val totalPages = response.getInt("totalPages")
                    isLastPage = page == totalPages - 1
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to parse JSON", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(this, "Failed to fetch caregivers", Toast.LENGTH_SHORT).show()
            }
        )

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentPage", currentPage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentPage = savedInstanceState.getInt("currentPage")
    }
}