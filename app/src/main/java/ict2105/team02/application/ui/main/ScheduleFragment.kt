package ict2105.team02.application.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.databinding.FragmentScheduleMainBinding
import ict2105.team02.application.schedule.CalendarFragment
import ict2105.team02.application.ui.ScheduleInfoFragment
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleMainBinding
    private lateinit var viewModel: ScheduleInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)
        binding = FragmentScheduleMainBinding.inflate(layoutInflater)
//        val mCalendar = binding.calendarView
//        viewModel.setScheduleByDate(DateDetails(Date(mCalendar.date)))
//        mCalendar.setOnDateChangeListener(){ calView, year, month, day ->
//            val calendar = Calendar.getInstance()
//            calendar.set(year, month, day)
//            val date = calendar.time
//            viewModel.setScheduleByDate(DateDetails(date))
//            Log.d("Test", "Selected date is $date")
//        }

        Log.d("onCreateViewTag", "Test1");
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSchedule = ScheduleInfoFragment()
        val fragmentCalendar = CalendarFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.frameLayoutScheduleInfo.id, fragmentSchedule)
        transaction.addToBackStack(null)
//        transaction.commit()
//        val transaction2 = childFragmentManager.beginTransaction()
        transaction.replace(binding.frameLayoutCalendar.id, fragmentCalendar)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d("TempSchedule", "Fragment Passed");
    }

}