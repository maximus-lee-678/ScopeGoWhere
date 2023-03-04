package ict2105.team02.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ict2105.team02.application.databinding.FragmentHomeBinding
import ict2105.team02.application.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val owner = requireActivity()

        viewModel.user.observe(owner) {
            binding.textViewUserName.text = it.name
            binding.textViewUserStaffId.text = "Staff ID: ${it.staffId.toString()}"
            binding.textViewUserDept.text = "Dept: ${it.department}"
        }

        viewModel.endoscopeStat.observe(owner) {
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