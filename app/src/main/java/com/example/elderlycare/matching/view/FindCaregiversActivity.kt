package com.example.elderlycare.matching.view

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matching_activity_find_caregivers)

        caregiversListView = findViewById(R.id.caregivers_listview)
        caregiversAdapter = CaregiversAdapter(this, caregiverList)
        caregiversListView.adapter = caregiversAdapter

        fetchCaregivers()
    }

    private fun fetchCaregivers() {
        val url = "http://10.100.103.28/m/matching/caregivers"

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

                        val caregiver = Caregiver(name, country, experience, certification, availableHours, image, gender)
                        caregiverList.add(caregiver)
                    }
                    caregiversAdapter.notifyDataSetChanged()
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
}