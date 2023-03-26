package ict2105.team02.application.ui.equipment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentEquipmentBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.dialogs.ScopeDetailDialogFragment
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_STATUS_FILTER
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.viewmodel.EquipmentListViewModel

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding
    private lateinit var eqAdapter: EquipmentAdapter
    private val equipmentListViewModel: EquipmentListViewModel by viewModels {
        ViewModelFactory(
            "EquipmentListViewModel",
            activity?.application as MainApplication
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEquipmentBinding.inflate(inflater)

        // Setup recyclerview
        eqAdapter = EquipmentAdapter()
        binding.equipmentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eqAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eqAdapter.onItemClick = {
            val fragment = ScopeDetailDialogFragment.newInstance(it.scopeSerial)
            fragment.show(requireActivity().supportFragmentManager, "scope_detail")
        }

        binding.buttonClear.setOnClickListener {
            binding.editTextSearch.text.clear()
            for (i in 0 until binding.chipGroupStatusFilters.childCount) {
                val chip = binding.chipGroupStatusFilters.getChildAt(i) as? Chip
                chip?.isChecked = false
            }
            equipmentListViewModel.clearFilters()
        }

        binding.buttonCreateScope.setOnClickListener {
            startActivity(Intent(requireContext(), AddScopeActivity::class.java))
        }

        // Endoscope search text change listener
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val search = s.toString()
                equipmentListViewModel.filterEquipmentByName(search)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        // Status filter chips checked change listener
        binding.chipGroupStatusFilters.setOnCheckedStateChangeListener { group, chipIds ->
            if (chipIds.size == 0) {
                equipmentListViewModel.clearFilters()
                return@setOnCheckedStateChangeListener
            }

            val selectedStatuses = mutableListOf<String>()
            for (chipId in chipIds) {
                Log.d("Chip", chipId.toString())
                val chip = group.findViewById<Chip>(chipId)
                if (chip != null) {
                    selectedStatuses.add(chip.text.toString())
                }
            }
            equipmentListViewModel.filterEquipmentByStatus(selectedStatuses)
        }

        // Bind view to view model
        equipmentListViewModel.equipments.observe(viewLifecycleOwner) {
            val filter = arguments?.getString(KEY_ENDOSCOPE_STATUS_FILTER)
            val uniqueStatuses = it.distinctBy { e -> e.scopeStatus }.map { e -> e.scopeStatus }
            binding.chipGroupStatusFilters.removeAllViews() // Clear children chips
            for (status in uniqueStatuses) {
                val statusChip = layoutInflater.inflate(
                    R.layout.chipgroup_status_chip,
                    binding.chipGroupStatusFilters,
                    false
                ) as Chip
                statusChip.apply { text = status }
                binding.chipGroupStatusFilters.addView(statusChip)
                Log.d(TAG, "$filter == $status")
                if (filter == status) {
                    binding.chipGroupStatusFilters.check(statusChip.id)
                }
            }
        }
        equipmentListViewModel.displayedEquipments.observe(viewLifecycleOwner) {
            eqAdapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()

        // Put here so it refreshes automatically when returning from other activities
        equipmentListViewModel.fetchEquipments {
            activity?.runOnUiThread {
                binding.loadEquipmentProgressIndicator.visibility = View.INVISIBLE
            }
        }
    }
}