package ict2105.team02.application.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentCalendarBinding
import ict2105.team02.application.recyclerview.CalendarAdapter

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)

        // Setup recyclerview
        val calendarAdapter = CalendarAdapter(42)
        binding.recyclerViewCalendarDay.apply {
            adapter = calendarAdapter
        }

        return binding.root
    }
}