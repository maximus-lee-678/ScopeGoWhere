package ict2105.team02.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentHomeBinding
import ict2105.team02.application.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val activity = requireActivity()

        viewModel.user.observe(activity) {
            binding.textViewUserName.text = it.name
            binding.textViewUserStaffId.text = getString(R.string.home_staff_id, it.staffId.toString())
            binding.textViewUserDept.text = getString(R.string.home_dept, it.department)
        }

        viewModel.endoscopeStat.observe(activity) {
            binding.textViewPendingSampleCount.text = it.pendingSample.toString()
            binding.textViewTotalEndoscopeCount.text = it.totalEndoscope.toString()
            binding.textViewInCirculationCount.text = it.inCirculation.toString()
            binding.textViewWashingCount.text = it.washing.toString()
            binding.textViewSamplingCount.text = it.sampling.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUserData()
        viewModel.fetchEndoscopeStats()
    }

}