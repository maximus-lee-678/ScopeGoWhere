package ict2105.team02.application.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.databinding.FragmentScheduleBinding
import ict2105.team02.application.schedule.CalendarFragment
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var viewModel: ScheduleInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)
        binding = FragmentScheduleBinding.inflate(layoutInflater)
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