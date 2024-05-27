package com.weather.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int { return 3 }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> Fragment1()
            1 -> Fragment2()
            2 -> Fragment3()
            else -> Fragment1()
        }
    }
}