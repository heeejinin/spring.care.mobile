package com.example.elderlycare.matching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        val nameTextView = view?.findViewById<TextView>(R.id.name_textview)
        val countryTextView = view?.findViewById<TextView>(R.id.country_textview)
        val experienceTextView = view?.findViewById<TextView>(R.id.experience_textview)
        val certificationTextView = view?.findViewById<TextView>(R.id.certification_textview)
        val availableHoursTextView = view?.findViewById<TextView>(R.id.available_hours_textview)

        nameTextView?.text = caregiver?.name
        countryTextView?.text = caregiver?.country
        experienceTextView?.text = caregiver?.experience
        certificationTextView?.text = caregiver?.certification
        availableHoursTextView?.text = caregiver?.availableHours

        return view!!
    }
}