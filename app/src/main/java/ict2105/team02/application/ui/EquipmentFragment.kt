package ict2105.team02.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentEquipmentBinding
import ict2105.team02.application.recyclerview.EquipmentAdapter
import ict2105.team02.application.viewmodel.EquipmentListViewModel

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding
    private lateinit var viewModel: EquipmentListViewModel

    private lateinit var eqAdapter: EquipmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEquipmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(EquipmentListViewModel::class.java)

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
            val fragment = ScopeDetailFragment.newInstance(it.serial)
            fragment.show(requireActivity().supportFragmentManager, "scope_detail")
        }

        viewModel.fetchEquipments {
            activity?.runOnUiThread {
                binding.loadEquipmentProgressIndicator.visibility = View.INVISIBLE
            }
        }
    }
}