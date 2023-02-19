package ict2105.team02.application.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.databinding.FragmentCalendarBinding
import ict2105.team02.application.recyclerview.CalendarAdapter
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding

    private lateinit var viewModel : ScheduleInfoViewModel
    val calendar : Calendar = Calendar.getInstance()
    var dateDetails : DateDetails = DateDetails(calendar.time)
    private lateinit var calendarAdapter : CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        Log.d("fish", dateDetails.toString())
        // Update viewModel
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)
        // Update View model
        calendarAdapter = CalendarAdapter(dateDetails,viewModel)

        binding.monthText.text = dateDetails.monthEnglish
        binding.yearText.text = dateDetails.year.toString()

        // Setup recyclerview, pass in DateDetails object with parameter of today's date in Date object
//        val calendarAdapter = CalendarAdapter(dateDetails)
        binding.recyclerViewCalendarDay.apply {
            adapter = calendarAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previousMonth.setOnClickListener { view ->
            Log.d("fish", "previousMonth")

            calendar.add(Calendar.MONTH, -1)
            calendarAdapter.selectedMonthOffset--

            refreshCalendar()
        }

        binding.nextMonth.setOnClickListener { view ->
            Log.d("fish", "nextMonth")
            calendar.add(Calendar.MONTH, 1)
            calendarAdapter.selectedMonthOffset++

            refreshCalendar()
        }
    }

    fun refreshCalendar(){
        dateDetails = DateDetails(calendar.time)
        binding.monthText.text = dateDetails.monthEnglish
        binding.yearText.text = dateDetails.year.toString()
        calendarAdapter.update(dateDetails)
    }
}

data class DateDetails(val todayDate: Date) {
    val day: Int = SimpleDateFormat("dd").format(todayDate).toInt()
    val month: Int = SimpleDateFormat("MM").format(todayDate).toInt()
    val monthEnglish : String = SimpleDateFormat("MMMM").format(todayDate)
    val year: Int = SimpleDateFormat("yyyy").format(todayDate).toInt()

    // Create a calendar object and set year, month(0 terminated), day(first)
    private val calendarObject: Calendar = GregorianCalendar(year, month - 1, 1)
    val daysInMonth: Int = calendarObject.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfMonth: Int = when (SimpleDateFormat("EEE").format(calendarObject.time)) { //
        "Mon" -> 1
        "Tue" -> 2
        "Wed" -> 3
        "Thu" -> 4
        "Fri" -> 5
        "Sat" -> 6
        "Sun" -> 0
        else -> {
            -1
        }
    }

    override fun toString(): String {
        return String.format(
            "%s || %d-%d(%s)-%d %d %d",
            todayDate,
            day,
            month,
            monthEnglish,
            year,
            daysInMonth,
            firstDayOfMonth
        )
    }


}