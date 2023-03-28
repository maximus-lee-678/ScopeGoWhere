package ict2105.team02.application.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ict2105.team02.application.databinding.FragmentScheduleMainBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleMainBinding

    // The two fragments must share a common ViewModel as one relies on the other
    private val scheduleInfoViewModel: ScheduleInfoViewModel by viewModels {
        ViewModelFactory(
            "ScheduleInfoViewModel", activity?.application as MainApplication
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSchedule = ScheduleInfoFragment(scheduleInfoViewModel)
        val fragmentCalendar = CalendarFragment(scheduleInfoViewModel)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.frameLayoutScheduleInfo.id, fragmentSchedule)
        transaction.addToBackStack(null)
        transaction.replace(binding.frameLayoutCalendar.id, fragmentCalendar)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}