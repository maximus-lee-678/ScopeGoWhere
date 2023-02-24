package ict2105.team02.application.schedule

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentScheduleBinding
import ict2105.team02.application.model.DateDetails
import ict2105.team02.application.recyclerview.CalendarMonthAdapter
import ict2105.team02.application.recyclerview.CalendarWeekAdapter
import ict2105.team02.application.storage.MainApplication
import ict2105.team02.application.viewmodel.CalendarViewModel
import ict2105.team02.application.viewmodel.CalendarViewModelFactory
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel
import java.text.SimpleDateFormat

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val TAG: String = CalendarFragment::class.simpleName!!
    private val daysInWeek: Int = 7

    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            (activity?.application as MainApplication).repository,
            (activity?.application as MainApplication)
        )
    }
    private lateinit var calendarMonthAdapter: CalendarMonthAdapter
    private lateinit var calendarWeekAdapter: CalendarWeekAdapter
    private lateinit var scheduleInfoViewModel: ScheduleInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calendarMonthAdapter = CalendarMonthAdapter(calendarViewModel.dateDetails.value!!)
        calendarWeekAdapter = CalendarWeekAdapter(calendarViewModel.dateDetails.value!!)

        binding = FragmentScheduleBinding.inflate(inflater)

        createListenersRecycler()               // Recyclerview listeners creation
        createListenersFragment()                   // Fragment listeners creation

        scheduleInfoViewModel =
            ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)

        // Add an observer on the LiveData returned by selectedDate
        calendarViewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            // Update the cached copy of the words in the adapter.
            date.let {
                calendarMonthAdapter.forceUpdateDate(date)
                calendarWeekAdapter.forceUpdateDate(date)
                updateModel(String.format("%d-%d-%d", it[0], it[1], it[2]))
            }

            Log.d(
                TAG,
                String.format(
                    "%s: date>%d-%d-%d",
                    object {}.javaClass.enclosingMethod.name,
                    date[0],
                    date[1],
                    date[2]
                )
            )
        }

        // Add an observer on the LiveData returned by selectedDate
        calendarViewModel.dateDetails.observe(viewLifecycleOwner) { dateDetails ->
            // Update the cached copy of the dates in the adapter.

            dateDetails.let {
                binding.monthText.text = dateDetails.monthEnglish
                binding.yearText.text = dateDetails.year.toString()

                calendarMonthAdapter.updateRecyclerContent(dateDetails)
                calendarWeekAdapter.updateRecyclerContent(dateDetails)
            }

            Log.d(
                TAG, "dateDetails: " + dateDetails.toString()
            )
        }

        // Add an observer on the LiveData returned by selectedDate
        calendarViewModel.scheduleLayoutType.observe(viewLifecycleOwner) { layoutType ->
            // Update the cached copy of the dates in the adapter.
            Log.d("fish", layoutType.toString())
            layoutType.let {
                setTheScene(it)
            }
        }

        return binding.root
    }

    fun updateModel(input: String) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(input)
        val dateDetails = DateDetails(date)
        // Update view Model.
        scheduleInfoViewModel.setScheduleByDate((dateDetails))
    }

    /**
     * Helper function that retrieves datastore's layoutType value.
     * Sets layout based on scheduleLayoutType:
     * True: Month Mode
     * False: Week Mode
     */
    private fun setTheScene(scheduleLayoutType: Boolean) {
        binding.switchScheduleViewType.isChecked = scheduleLayoutType!!
        Log.d("fish", scheduleLayoutType.toString())
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
    private fun createListenersFragment() {
        // Previous button: Update month or week, indicate page offset changed, refresh layout
        binding.buttonSchedulePrevious.setOnClickListener { view ->
            Log.d(TAG, "buttonSchedulePrevious")

            if (calendarViewModel.scheduleLayoutType.value!!) {
                calendarViewModel.updateSelectedPeriodStep("month", -1)
            } else {
                calendarViewModel.updateSelectedPeriodStep("week", -1)
            }

            Log.d(
                TAG,
                String.format("%s: clicked>%s", object {}.javaClass.enclosingMethod.name, "prev")
            )
        }

        // Next button: Update month or week, indicate page offset changed, refresh layout
        binding.buttonScheduleNext.setOnClickListener { view ->
            Log.d(TAG, "buttonScheduleNext")
            if (calendarViewModel.scheduleLayoutType.value!!) {
                calendarViewModel.updateSelectedPeriodStep("month", 1)
            } else {
                calendarViewModel.updateSelectedPeriodStep("week", 1)
            }

            Log.d(
                TAG,
                String.format("%s: clicked>%s", object {}.javaClass.enclosingMethod.name, "next")
            )
        }

        // Layout button: Update layout to month or week
        binding.switchScheduleViewType.setOnClickListener { view ->
            Log.d(TAG, "switchScheduleViewType")
            val switch: Switch = view as Switch

            calendarViewModel.forceAlignCalendar()
            calendarViewModel.updateLayoutType(switch.isChecked)

            Log.d(
                TAG,
                String.format(
                    "%s: stored and switched to>%s",
                    object {}.javaClass.enclosingMethod.name,
                    switch.isChecked
                )
            )
        }
    }

    /**
     * Creates listeners that react to items being clicked in recyclerview.
     * Stores clicked date in selectedDate.
     */
    private fun createListenersRecycler() {
        calendarWeekAdapter.onItemClick = { onItemClick ->
            calendarViewModel.updateSelectedDateExplicit(onItemClick)
        }

        calendarMonthAdapter.onItemClick = { onItemClick ->
            calendarViewModel.updateSelectedDateExplicit(onItemClick)
        }
    }
}