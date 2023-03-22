package ict2105.team02.application.ui.sample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SampleFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return Sample1FluidResultFragment()
            1 -> return Sample2SwabResultFragment()
            2 -> return Sample3RepeatOfMsFragment()
            3 -> return Sample4AtpFragment()
            else -> return Sample5ReviewFragment()
        }
    }
}
