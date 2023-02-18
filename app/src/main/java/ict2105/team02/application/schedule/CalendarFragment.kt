package ict2105.team02.application.schedule

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentScheduleBinding
import ict2105.team02.application.recyclerview.CalendarMonthAdapter
import ict2105.team02.application.recyclerview.CalendarWeekAdapter
import ict2105.team02.application.storage.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding

    val calendar: Calendar = Calendar.getInstance()
    var dateDetails: DateDetails = DateDetails(calendar.time)
    var scheduleLayoutType: Boolean? = null
    var calendarMonthAdapter = CalendarMonthAdapter(dateDetails)
    var calendarWeekAdapter = CalendarWeekAdapter(dateDetails)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            binding = FragmentScheduleBinding.inflate(inflater)

            Log.d("fish", dateDetails.toString())
            binding.monthText.text = dateDetails.monthEnglish
            binding.yearText.text = dateDetails.year.toString()

            // gets preference datastore value
            getLayout()
            Log.d("fish", scheduleLayoutType.toString())

            setTheScene(scheduleLayoutType!!)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSchedulePrevious.setOnClickListener { view ->
            Log.d("fish", "previous")

            if(scheduleLayoutType!!){
                calendar.add(Calendar.MONTH, -1)
                calendarMonthAdapter.selectedMonthOffset--
            }
            else{
                calendar.add(Calendar.WEEK_OF_MONTH, -1)
                calendarWeekAdapter.selectedWeekOffset--
            }

            refreshCalendar()
        }

        binding.buttonScheduleNext.setOnClickListener { view ->
            Log.d("fish", "next")
            if(scheduleLayoutType!!){
                calendar.add(Calendar.MONTH, 1)
                calendarMonthAdapter.selectedMonthOffset++
            }
            else{
                calendar.add(Calendar.WEEK_OF_MONTH, 1)
                calendarWeekAdapter.selectedWeekOffset++
            }

            refreshCalendar()
        }

        binding.switchScheduleViewType.setOnClickListener { view ->
            val switch: Switch = view as Switch

            scheduleLayoutType = switch.isChecked

            setTheScene(scheduleLayoutType!!)

            // refresh dates for both
            calendarMonthAdapter.update(dateDetails)
            calendarWeekAdapter.update(dateDetails)

            // store to datastore
            lifecycleScope.launch {
                UserPreferencesRepository.getInstance(activity as Context)
                    .updateLayoutType(scheduleLayoutType!!, activity as Context)
            }
        }
    }

    fun refreshCalendar() {
        dateDetails = DateDetails(calendar.time)

        binding.monthText.text = dateDetails.monthEnglish
        binding.yearText.text = dateDetails.year.toString()
        calendarMonthAdapter.update(dateDetails)
        calendarWeekAdapter.update(dateDetails)
    }

    /**
     * Helper function that retrieves datastore's layoutType value.
     */
    suspend fun getLayout() {
        val repoObject =
            UserPreferencesRepository.getInstance(activity as Context).userPreferencesFlow
        scheduleLayoutType = repoObject.first().scheduleLayoutType
    }

    fun setTheScene(scheduleLayoutType: Boolean) {
        binding.switchScheduleViewType.isChecked = scheduleLayoutType!!

        if (scheduleLayoutType) {
            binding.linearLayoutWeekDays.visibility = View.VISIBLE
            binding.recyclerViewCalendarDay.adapter = calendarMonthAdapter
            binding.recyclerViewCalendarDay.layoutManager =
                GridLayoutManager(activity as Context, 7)
        } else {
            binding.linearLayoutWeekDays.visibility = View.GONE
            binding.recyclerViewCalendarDay.adapter = calendarWeekAdapter
            binding.recyclerViewCalendarDay.layoutManager =
                LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
        }
    }
}

data class DateDetails(val todayDate: Date) {
    var day: Int? = null
    var month: Int? = null
    var monthEnglish: String? = null
    var year: Int? = null
    var daysInMonth: Int? = null
    var firstDayOfMonth: Int? = null
    var weekArray : Array<IntArray> = Array(7) { IntArray(3) }

    init {
        day = SimpleDateFormat("dd").format(todayDate).toInt()
        month = SimpleDateFormat("MM").format(todayDate).toInt()
        monthEnglish = SimpleDateFormat("MMMM").format(todayDate)
        year = SimpleDateFormat("yyyy").format(todayDate).toInt()

        // Create a calendar object and set year, month(0 terminated), day(first)
        // Get number of days and first day's int value
        var calendarObject: Calendar = GregorianCalendar(year!!, month!! - 1, 1)
        daysInMonth = calendarObject.getActualMaximum(Calendar.DAY_OF_MONTH)
        firstDayOfMonth = when (SimpleDateFormat("EEE").format(calendarObject.time)) { //
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

        // Update calendar to current day, then first day of that week
        // Get monday's int value(negated if last month)
        calendarObject = GregorianCalendar(year!!, month!! - 1, day!!)
        Log.d("fish", calendarObject.time.toString())    // ??????? observation needed
        calendarObject.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
//        Log.d("fish", calendarObject.time.toString())

        for (i in 0..6){
            if(i + 2 <= 7) i + 2 else i + 2 - 7
            calendarObject.set(Calendar.DAY_OF_WEEK, if(i + 2 <= 7) i + 2 else i + 2 - 7)
            weekArray[i][0] = SimpleDateFormat("dd").format(calendarObject.time).toInt()
            weekArray[i][1] = SimpleDateFormat("MM").format(calendarObject.time).toInt()
            weekArray[i][2] = SimpleDateFormat("yyyy").format(calendarObject.time).toInt()
            Log.d("fish", String.format(
                "%d-%d-%d", weekArray[i][0], weekArray[i][1],  weekArray[i][2]))
        }
    }

    override fun toString(): String {
        return String.format(
            "%s || %d-%d(%s)-%d t_days:%d",
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