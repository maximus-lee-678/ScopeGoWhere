package ict2105.team02.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentHomeBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.equipment.EquipmentFragment
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.utils.Constants
import ict2105.team02.application.viewmodel.EquipmentListViewModel
import ict2105.team02.application.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(
            "HomeViewModel",
            activity?.application as MainApplication
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val activity = requireActivity()
        val mainActivity = requireActivity() as MainActivity

        homeViewModel.user.observe(activity) {
            binding.textViewUserName.text = it.name
            binding.textViewUserStaffId.text = getString(R.string.home_staff_id, it.staffId.toString())
            binding.textViewUserDept.text = getString(R.string.home_dept, it.department)
        }

        homeViewModel.endoscopeStat.observe(activity) {
            binding.textViewPendingSampleCount.text = it.pendingSample.toString()
            binding.textViewTotalEndoscopeCount.text = it.totalEndoscope.toString()
            binding.textViewInCirculationCount.text = it.inCirculation.toString()
            binding.textViewWashingCount.text = it.washing.toString()
            binding.textViewSamplingCount.text = it.sampling.toString()
        }

        binding.linearLayoutPendingSample.setOnClickListener {
            mainActivity.navbarNavigate(ScheduleFragment())
        }

        binding.linearLayoutAllEndoscope.setOnClickListener {
            mainActivity.navbarNavigate(EquipmentFragment())
        }

        binding.linearLayoutCirculation.setOnClickListener {
            mainActivity.navbarNavigate(EquipmentFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.KEY_ENDOSCOPE_STATUS_FILTER, Constants.ENDOSCOPE_CIRCULATION)
                }
            })
        }

        binding.linearLayoutWashing.setOnClickListener {
            mainActivity.navbarNavigate(EquipmentFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.KEY_ENDOSCOPE_STATUS_FILTER, Constants.ENDOSCOPE_WASH)
                }
            })
        }

        binding.linearLayoutSampling.setOnClickListener {
            mainActivity.navbarNavigate(EquipmentFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.KEY_ENDOSCOPE_STATUS_FILTER, Constants.ENDOSCOPE_SAMPLE)
                }
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.fetchUserData()
        homeViewModel.fetchEndoscopeStats()
    }
}