package ict2105.team02.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentScheduleInfoBinding
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel
import ict2105.team02.application.adapter.ScheduleAdapter

class ScheduleInfoFragment : Fragment() {
    private lateinit var adapter: ScheduleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentScheduleInfoBinding
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: ScheduleInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScheduleInfoBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.scheduleRecycle)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        val data = viewModel.getSchedule()?.getValue()
        adapter = ScheduleAdapter(data)
        recyclerView.adapter = adapter
    }

}