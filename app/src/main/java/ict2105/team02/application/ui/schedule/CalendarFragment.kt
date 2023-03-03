package ict2105.team02.application.ui.schedule

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
    private val TAG: String = this::class.simpleName!!
    private val daysInWeek: Int = 7

    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            (activity?.application as MainApplication).repository,
            (activity?.application as MainApplication)
        )
    }
    private lateinit var scheduleInfoViewModel: ScheduleInfoViewModel
    private lateinit var calendarMonthAdapter: CalendarMonthAdapter
    private lateinit var calendarWeekAdapter: CalendarWeekAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Spawn adapters, contents nullified
        calendarMonthAdapter = CalendarMonthAdapter(activity as Context)
        calendarWeekAdapter = CalendarWeekAdapter(activity as Context)

        binding = FragmentScheduleBinding.inflate(inflater)

        createListenersRecycler()   // Recyclerview listeners creation
        createListenersFragment()   // Fragment listeners creation
        createObservers()           // Data observers creation

        scheduleInfoViewModel =
            ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)

        return binding.root
    }

    /**
     * Updates scheduleInfoViewModel.
     */
    private fun updateModel(input: String) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(input)
        // Update view Model.
        scheduleInfoViewModel.setScheduleByDate((date))
    }

    /**
     * Helper function that retrieves datastore's layoutType value.
     * Sets layout based on scheduleLayoutType:
     * True: Month Mode
     * False: Week Mode
     */
    private fun setTheScene(scheduleLayoutType: Boolean) {
        binding.switchScheduleViewType.isChecked = scheduleLayoutType!!
        // month
        if (scheduleLayoutType) {
            binding.linearLayoutWeekDays.visibility = View.GONE
            binding.linearLayoutMonthDays.visibility = View.VISIBLE
            binding.recyclerViewCalendarDay.adapter = calendarMonthAdapter
            binding.recyclerViewCalendarDay.layoutManager =
                GridLayoutManager(activity as Context, daysInWeek)
        }
        // week
        else {
            binding.linearLayoutWeekDays.visibility = View.VISIBLE
            binding.linearLayoutMonthDays.visibility = View.GONE
            binding.recyclerViewCalendarDay.adapter = calendarWeekAdapter
            binding.recyclerViewCalendarDay.layoutManager =
                LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     * Creates observers.
     */
    private fun createObservers() {
        // Add an observer on the LiveData returned by selectedDate
        calendarViewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            // Update selectedDate in the adapter
            calendarMonthAdapter.forceUpdateDate(selectedDate)
            calendarWeekAdapter.forceUpdateDate(selectedDate)

            // Update scheduleInfoViewModel
            updateModel(
                String.format(
                    "%d-%d-%d",
                    selectedDate[0],
                    selectedDate[1],
                    selectedDate[2]
                )
            )

            Log.d(
                TAG, String.format(
                    "[%s] selectedDate: %2d-%2d-%4d",
                    object {}.javaClass.enclosingMethod!!.name,
                    selectedDate[0],
                    selectedDate[1],
                    selectedDate[2]
                )
            )
        }

        // Add an observer on the LiveData returned by dateDetails
        calendarViewModel.dateDetails.observe(viewLifecycleOwner) { dateDetails ->
            // Update header of fragment
            binding.monthText.text = dateDetails.monthEnglish
            binding.yearText.text = dateDetails.year.toString()

            // Update recyclerView contents, forces reload of layout
            calendarMonthAdapter.updateRecyclerContent(dateDetails)
            calendarWeekAdapter.updateRecyclerContent(dateDetails)

            Log.d(
                TAG,
                String.format(
                    "[%s] dateDetails: %s",
                    object {}.javaClass.enclosingMethod!!.name,
                    dateDetails.toString()
                )
            )
        }

        // Add an observer on the LiveData returned by scheduleLayoutType
        calendarViewModel.scheduleLayoutType.observe(viewLifecycleOwner) { layoutType ->
            // Update layout
            setTheScene(layoutType)

            Log.d(
                TAG,
                String.format(
                    "[%s] layoutType: %s",
                    object {}.javaClass.enclosingMethod!!.name,
                    layoutType.toString()
                )
            )
        }
    }

    /**
     * Creates listeners for previous, next buttons and mode switch.
     */
    private fun createListenersFragment() {
        // Previous button: Update month or week, indicate page offset changed, refresh layout
        binding.buttonSchedulePrevious.setOnClickListener { view ->
            if (calendarViewModel.scheduleLayoutType.value!!) {
                calendarViewModel.updateSelectedPeriodStep("month", -1)
            } else {
                calendarViewModel.updateSelectedPeriodStep("week", -1)
            }

            Log.d(
                TAG,
                String.format(
                    "[%s] buttonSchedulePrevious",
                    object {}.javaClass.enclosingMethod!!.name
                )
            )
        }

        // Next button: Update month or week, indicate page offset changed, refresh layout
        binding.buttonScheduleNext.setOnClickListener { view ->
            if (calendarViewModel.scheduleLayoutType.value!!) {
                calendarViewModel.updateSelectedPeriodStep("month", 1)
            } else {
                calendarViewModel.updateSelectedPeriodStep("week", 1)
            }

            Log.d(
                TAG,
                String.format(
                    "[%s] buttonScheduleNext",
                    object {}.javaClass.enclosingMethod!!.name
                )
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
                    "[%s] switchScheduleViewType: %s",
                    object {}.javaClass.enclosingMethod!!.name,
                    switch.isChecked
                )
            )
        }
    }

    /**
     * Creates listeners that react to items being clicked in recyclerview.
     * Updates selectedDate in ViewModel.
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
