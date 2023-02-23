package ict2105.team02.application.schedule

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentScheduleBinding
import ict2105.team02.application.recyclerview.CalendarMonthAdapter
import ict2105.team02.application.recyclerview.CalendarWeekAdapter
import ict2105.team02.application.repo.UserPreferencesRepository
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val TAG: String = CalendarFragment::class.simpleName!!
    private val daysInWeek: Int = 7

    private val calendar: Calendar = Calendar.getInstance()
    private var dateDetails: DateDetails = DateDetails(calendar.time)
    private var scheduleLayoutType: Boolean? = null
    private var calendarMonthAdapter: CalendarMonthAdapter = CalendarMonthAdapter(dateDetails)
    private var calendarWeekAdapter: CalendarWeekAdapter = CalendarWeekAdapter(dateDetails)
    private var selectedDate: IntArray = intArrayOf(dateDetails.day!!, dateDetails.month!!, dateDetails.year!!)

    private lateinit var viewModel : ScheduleInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            binding = FragmentScheduleBinding.inflate(inflater)

            refreshCalendar(hardRefresh = false)    // Update month and year. No need to refresh, values already initialised
            getLayout()                             // Gets preference datastore value
            setTheScene(scheduleLayoutType!!)       // Sets recyclerview layout
            createListenersRecycler()               // Recyclerview listeners creation
        }
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)
        Log.d(TAG, String.format("%s: date>%d-%d-%d", object {}.javaClass.enclosingMethod.name, selectedDate[0], selectedDate[1], selectedDate[2]))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createListenersFragment()                   // Fragment listeners creation
    }
    fun updateModel(input : String){
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(input)
        val dateDetails = DateDetails(date)
        // Update view Model.
        viewModel.setScheduleByDate((dateDetails))
        Log.d("CalandarAdaptor", "Cal")
    }
    /**
     * Updates selected month and year.
     * Based on hardRefresh value:
     * True: Reloads recyclerview items and headers
     * False: Just reloads headers
     */
    private fun refreshCalendar(hardRefresh: Boolean) {
        dateDetails = DateDetails(calendar.time)
        binding.monthText.text = dateDetails.monthEnglish
        binding.yearText.text = dateDetails.year.toString()

        if (hardRefresh) {
            calendarMonthAdapter.update(dateDetails)
            calendarWeekAdapter.update(dateDetails)
        }
    }

    /**
     * Helper function that retrieves datastore's layoutType value.
     * Sets scheduleLayoutType:
     * True: Month Mode
     * False: Week Mode
     */
    private suspend fun getLayout() {
        val repoObject =
            UserPreferencesRepository.getInstance(activity as Context).userPreferencesFlow
        scheduleLayoutType = repoObject.first().scheduleLayoutType
    }

    /**
     * Helper function that retrieves datastore's layoutType value.
     * Sets layout based on scheduleLayoutType:
     * True: Month Mode
     * False: Week Mode
     */
    private fun setTheScene(scheduleLayoutType: Boolean) {
        binding.switchScheduleViewType.isChecked = scheduleLayoutType!!

        if (scheduleLayoutType) {
            binding.linearLayoutWeekDays.visibility = View.VISIBLE
            binding.recyclerViewCalendarDay.adapter = calendarMonthAdapter
            binding.recyclerViewCalendarDay.layoutManager =
                GridLayoutManager(activity as Context, daysInWeek)
        } else {
            binding.linearLayoutWeekDays.visibility = View.GONE
            binding.recyclerViewCalendarDay.adapter = calendarWeekAdapter
            binding.recyclerViewCalendarDay.layoutManager =
                LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
        }
    }
    /**
     * Creates listeners for previous, next buttons and mode switch.
     */
    private fun createListenersFragment(){
        // Previous button: Update month or week, indicate page offset changed, refresh layout
        binding.buttonSchedulePrevious.setOnClickListener { view ->
            Log.d("fish", "previous")

            if (scheduleLayoutType!!) {
                calendar.add(Calendar.MONTH, -1)
                calendarMonthAdapter.selectedMonthOffset--
            } else {
                calendar.add(Calendar.WEEK_OF_MONTH, -1)
                calendarWeekAdapter.selectedWeekOffset--
            }

            refreshCalendar(hardRefresh = true)
            Log.d(TAG, String.format("%s: clicked>%s", object {}.javaClass.enclosingMethod.name, "prev"))
        }

        // Next button: Update month or week, indicate page offset changed, refresh layout
        binding.buttonScheduleNext.setOnClickListener { view ->
            Log.d("fish", "next")
            if (scheduleLayoutType!!) {
                calendar.add(Calendar.MONTH, 1)
                calendarMonthAdapter.selectedMonthOffset++
            } else {
                calendar.add(Calendar.WEEK_OF_MONTH, 1)
                calendarWeekAdapter.selectedWeekOffset++
            }

            refreshCalendar(hardRefresh = true)
            Log.d(TAG, String.format("%s: clicked>%s", object {}.javaClass.enclosingMethod.name, "next"))
        }

        // Layout button: Update layout to month or week
        binding.switchScheduleViewType.setOnClickListener { view ->
            val switch: Switch = view as Switch

            scheduleLayoutType = switch.isChecked

            setTheScene(scheduleLayoutType!!)

            refreshCalendar(hardRefresh = true)

            // store to datastore
            lifecycleScope.launch {
                UserPreferencesRepository.getInstance(activity as Context)
                    .updateLayoutType(scheduleLayoutType!!, activity as Context)
            }

            Log.d(TAG, String.format("%s: stored and switched to>%s", object {}.javaClass.enclosingMethod.name, scheduleLayoutType))
        }
    }

    /**
     * Creates listeners that react to items being clicked in recyclerview.
     * Stores clicked date in selectedDate.
     */
    private fun createListenersRecycler() {
        calendarWeekAdapter.onItemClick = { onItemClick ->
            selectedDate[0] = onItemClick[0]
            selectedDate[1] = onItemClick[1]
            selectedDate[2] = onItemClick[2]

            calendar.set(onItemClick[2], onItemClick[1] - 1, onItemClick[0])
            updateModel(String.format("%d-%d-%d",selectedDate[0], selectedDate[1], selectedDate[2]))
            refreshCalendar(hardRefresh = false)

            Log.d(TAG, String.format("%s: date>%d-%d-%d", object {}.javaClass.enclosingMethod.name, selectedDate[0], selectedDate[1], selectedDate[2]))
        }

        calendarMonthAdapter.onItemClick = { onItemClick ->
            selectedDate[0] = onItemClick[0]
            selectedDate[1] = onItemClick[1]
            selectedDate[2] = onItemClick[2]

            calendar.set(onItemClick[2], onItemClick[1] - 1, onItemClick[0])
            updateModel(String.format("%d-%d-%d",selectedDate[0], selectedDate[1], selectedDate[2]))
            refreshCalendar(hardRefresh = false)

            Log.d(TAG, String.format("%s: date>%d-%d-%d", object {}.javaClass.enclosingMethod.name, selectedDate[0], selectedDate[1], selectedDate[2]))
        }
    }
}

data class DateDetails(val todayDate: Date) {
    private val TAG: String = CalendarFragment::class.simpleName!!
    var day: Int? = null
    var month: Int? = null
    var monthEnglish: String? = null
    var year: Int? = null
    var daysInMonth: Int? = null
    var firstDayOfMonth: Int? = null
    var weekArray: Array<IntArray> = Array(7) { IntArray(3) }

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
        Log.d(TAG, String.format("This log is important don't delete: accessed>%s", calendarObject.time.toString()))
        calendarObject.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        for (i in 0..6) {
            calendarObject.set(Calendar.DAY_OF_WEEK, if (i + 2 <= 7) i + 2 else i + 2 - 7)
            weekArray[i][0] = SimpleDateFormat("dd").format(calendarObject.time).toInt()
            weekArray[i][1] = SimpleDateFormat("MM").format(calendarObject.time).toInt()
            weekArray[i][2] = SimpleDateFormat("yyyy").format(calendarObject.time).toInt()
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