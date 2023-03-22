package ict2105.team02.application.ui.wash

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WashFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return Wash1WasherFragment()
            1 -> return Wash2DetergentFragment()
            2 -> return Wash3DisinfectantFragment()
            3 -> return Wash4DryingCabinetFragment()
            else -> return Wash5ReviewFragment()
        }
    }
}
