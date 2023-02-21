package ict2105.team02.application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentTodayScheduleBinding
import ict2105.team02.application.recyclerview.EquipmentAdapter
import ict2105.team02.application.viewmodel.TodayScheduleViewModel


class TodaySchedule : Fragment() {
    private lateinit var binding : FragmentTodayScheduleBinding
    private lateinit var viewModel : TodayScheduleViewModel
    private lateinit var eadapter: EquipmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayScheduleBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TodayScheduleViewModel::class.java)

        eadapter = EquipmentAdapter()
        binding.todayScheduleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eadapter
        }

        viewModel.equipments.observe(viewLifecycleOwner){
            eadapter.submitList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eadapter.onItemClick = {
            val fragment = ScopeDetailFragment.newInstance(it.serial)
            fragment.show(requireActivity().supportFragmentManager, "scope_detail")
        }
        viewModel.fetchTodaySchedules {
            activity?.runOnUiThread {
                binding.loadEquipmentProgressIndicator.visibility = View.INVISIBLE
            }
        }
    }

}