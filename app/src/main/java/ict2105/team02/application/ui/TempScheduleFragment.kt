package ict2105.team02.application.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentTempScheduleBinding
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel
import java.text.SimpleDateFormat
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
class TempScheduleFragment : Fragment() {
    private lateinit var binding: FragmentTempScheduleBinding
    private lateinit var viewModel: ScheduleInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)
        binding = FragmentTempScheduleBinding.inflate(layoutInflater)
        val mCalendar = binding.calendarView
        viewModel.getScheduleByDate(Date(mCalendar.date))
        mCalendar.setOnDateChangeListener(){ calView, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val date = calendar.time
            viewModel.getScheduleByDate(date)
            Log.d("Test", "Selected date is $date")
        }

        Log.d("onCreateViewTag", "Test1");
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = ScheduleInfoFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.frameLayout.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d("TempSchedule", "Fragment Passed");
    }

}