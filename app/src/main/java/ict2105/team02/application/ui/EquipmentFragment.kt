package ict2105.team02.application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentEquipmentBinding
import ict2105.team02.application.recyclerview.EquipmentAdapter
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.viewmodel.EquipmentListViewModel
import java.util.*

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding
    private lateinit var viewModel: EquipmentListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEquipmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(EquipmentListViewModel::class.java)

        // Setup recyclerview
        val eqAdapter = EquipmentAdapter()
        binding.equipmentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eqAdapter
        }

        // Bind view to view model
        viewModel.equipments.observe(viewLifecycleOwner){
            eqAdapter.submitList(it)
        }

        viewModel.fetchEquipments()

        return binding.root
    }
}