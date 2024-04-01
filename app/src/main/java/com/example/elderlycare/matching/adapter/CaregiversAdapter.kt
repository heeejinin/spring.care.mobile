package com.example.elderlycare.matching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.elderlycare.R
import com.example.elderlycare.matching.model.Caregiver

class CaregiversAdapter(context: Context, private val caregivers: List<Caregiver>) :
    ArrayAdapter<Caregiver>(context, 0, caregivers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.matching_item_caregiver, parent, false)
        }

        val caregiver = getItem(position)

        val imageView = view?.findViewById<ImageView>(R.id.caregiver_image)
        val nameTextView = view?.findViewById<TextView>(R.id.name_textview)
        val countryTextView = view?.findViewById<TextView>(R.id.country_textview)
        val experienceTextView = view?.findViewById<TextView>(R.id.experience_textview)
        val certificationTextView = view?.findViewById<TextView>(R.id.certification_textview)
        val availableHoursTextView = view?.findViewById<TextView>(R.id.available_hours_textview)
        val detailsButton = view?.findViewById<Button>(R.id.details_button)
        val requestButton = view?.findViewById<Button>(R.id.request_button)

        // 이미지 로드
        val imageName = caregiver?.image
        val imageResourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        if (imageResourceId != 0) {
            imageView?.setImageResource(imageResourceId)
        } else {
            val defaultImage = if (caregiver?.gender == "Male") R.drawable.defaultmale else R.drawable.defaultfemale
            imageView?.setImageResource(defaultImage)
        }

        nameTextView?.text = caregiver?.name
        countryTextView?.text = caregiver?.country
        experienceTextView?.text = caregiver?.experience
        certificationTextView?.text = caregiver?.certification
        availableHoursTextView?.text = caregiver?.availableHours

        // 상세보기 버튼 클릭 리스너 설정
        detailsButton?.setOnClickListener {
            // 상세보기 기능 구현
        }

        // 요청 버튼 클릭 리스너 설정
        requestButton?.setOnClickListener {
            // 요청 기능 구현
        }

        return view!!
    }
}