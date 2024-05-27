package com.weather.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2

class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_1, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.guide_ViewPager)

        rootView.findViewById<Button>(R.id.next_Button).setOnClickListener {
            viewPager!!.currentItem = 1
        }

        return rootView
    }
}