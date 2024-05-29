package com.weather.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.weather.app.activities.App_Guide
import com.weather.app.R

class Fragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_3, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.guide_ViewPager)

        rootView.findViewById<Button>(R.id.previous_Button).setOnClickListener {
            viewPager!!.currentItem = 1
        }

        rootView.findViewById<Button>(R.id.next_Button).setOnClickListener {
            (requireActivity() as App_Guide).launchApp()
        }

        return rootView
    }
}