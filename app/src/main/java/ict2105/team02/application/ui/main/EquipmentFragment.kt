package ict2105.team02.application.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentEquipmentBinding
import ict2105.team02.application.recyclerview.EquipmentAdapter
import ict2105.team02.application.ui.dialogs.ScopeDetailFragment
import ict2105.team02.application.viewmodel.EquipmentListViewModel

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding
    private lateinit var eqAdapter: EquipmentAdapter
    private val TAG: String = this::class.simpleName!!

    private val viewModel by viewModels<EquipmentListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEquipmentBinding.inflate(inflater)

        // Setup recyclerview
        eqAdapter = EquipmentAdapter()
        binding.equipmentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eqAdapter
        }

        // Initialize spinner
        val spinner: Spinner = binding.equipmentSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipmentSpinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Don't load if still retrieving equipment (very scuffed)
                    if (binding.loadEquipmentProgressIndicator.visibility == View.VISIBLE) {
                        return
                    }

                    val selectedStatus = parent?.getItemAtPosition(position).toString()
                    makeToastIfZero(viewModel.filterEquipmentStatus(selectedStatus))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        // Bind view to view model
        viewModel.filteredEquipment.observe(viewLifecycleOwner) { filtered ->
            eqAdapter.submitList(filtered)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eqAdapter.onItemClick = {
            val fragment = ScopeDetailFragment.newInstance(it.scopeSerial)
            fragment.show(requireActivity().supportFragmentManager, "scope_detail")
        }

        binding.buttonSearch.setOnClickListener {
            makeToastIfZero(
                viewModel.filterEquipmentSerial(
                    binding.equipmentSpinner.selectedItem.toString(),
                    binding.editTextSearch.text.toString()
                )
            )
        }

        binding.buttonClear.setOnClickListener {
            binding.editTextSearch.text.clear()
            makeToastIfZero(viewModel.filterEquipmentStatus(binding.equipmentSpinner.selectedItem.toString()))
        }

        viewModel.fetchEquipments {
            activity?.runOnUiThread {
                binding.loadEquipmentProgressIndicator.visibility = View.INVISIBLE
            }
        }
    }

    /**
     * Helper function that creates a piece of toast if it receives a 0.
     * Called on equipment searches, notifies users if no scopes were found.
     */
    fun makeToastIfZero(itemCount: Int) {
        if (itemCount == 0) Toast.makeText(
            activity,
            getString(R.string.error_360_no_scopes),
            Toast.LENGTH_SHORT
        ).show()
    }
}