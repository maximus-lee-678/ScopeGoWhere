package ict2105.team02.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentEquipmentBinding
import ict2105.team02.application.recyclerview.EquipmentAdapter
import ict2105.team02.application.ui.dialogs.ScopeDetailFragment
import ict2105.team02.application.viewmodel.EquipmentListViewModel

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding
    private lateinit var eqAdapter: EquipmentAdapter

    private val viewModel by viewModels<EquipmentListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEquipmentBinding.inflate(inflater)

        // Setup recyclerview
        eqAdapter = EquipmentAdapter()
        binding.equipmentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eqAdapter
        }

        // Bind view to view model
        viewModel.equipments.observe(viewLifecycleOwner){
            eqAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eqAdapter.onItemClick = {
            val fragment = ScopeDetailFragment.newInstance(it.ScopeSerial)
            fragment.show(requireActivity().supportFragmentManager, "scope_detail")
        }

        viewModel.fetchEquipments {
            activity?.runOnUiThread {
                binding.loadEquipmentProgressIndicator.visibility = View.INVISIBLE
            }
        }
    }
}