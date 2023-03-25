package ict2105.team02.application.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentHomeBinding
import ict2105.team02.application.ui.sample.SampleActivity
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.utils.Constants
import ict2105.team02.application.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val activity = requireActivity()
        val mainActivity = requireActivity() as MainActivity

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

        binding.linearLayoutPendingSample.setOnClickListener {
            createMainActivityIntent("schedule")
        }

        binding.linearLayoutAllEndoscope.setOnClickListener {
            createMainActivityIntent("equipment")
        }

        binding.linearLayoutCirculation.setOnClickListener {
            createMainActivityIntent("equipment", Constants.ENDOSCOPE_CIRCULATION)
        }

        binding.linearLayoutWashing.setOnClickListener {
            createMainActivityIntent("equipment", Constants.ENDOSCOPE_WASH)
        }

        binding.linearLayoutSampling.setOnClickListener {
            createMainActivityIntent("equipment", Constants.ENDOSCOPE_SAMPLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUserData()
        viewModel.fetchEndoscopeStats()
    }
    private fun createMainActivityIntent(fragment: String, equipmentFilter: String? = null){
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(getString(R.string.main_activity_intent_fragment), fragment)
        equipmentFilter?.let {
            intent.putExtra(getString(R.string.equipment_filter), it)
        }
        startActivity(intent)
        activity?.finish()
    }
}