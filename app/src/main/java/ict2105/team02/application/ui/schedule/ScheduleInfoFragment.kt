package ict2105.team02.application.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentScheduleInfoBinding
import ict2105.team02.application.ui.equipment.EquipmentAdapter
import ict2105.team02.application.ui.dialogs.ScopeDetailDialogFragment
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel

class ScheduleInfoFragment : Fragment() {
    private lateinit var eqAdapter: EquipmentAdapter
    private lateinit var binding: FragmentScheduleInfoBinding
    private lateinit var viewModel: ScheduleInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScheduleInfoBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(ScheduleInfoViewModel::class.java)

        binding.scheduleRecycle.setHasFixedSize(false)
        eqAdapter = EquipmentAdapter()

        binding.scheduleRecycle.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = eqAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set onItemClick before observing the LiveData
        eqAdapter.onItemClick = {
            val fragment = ScopeDetailDialogFragment.newInstance(it.scopeSerial)
            Log.d("EquipmentAdapter", "Setup OnClick")
            fragment.show(requireActivity().supportFragmentManager, "scope_detail")

        }


        viewModel.fetchAllScheduledScope() {
            activity?.runOnUiThread {
                binding.loadEquipmentProgressIndicator.visibility = View.INVISIBLE
            }
        }

        viewModel.getScheduledEndoscope()?.observe(viewLifecycleOwner) {
            eqAdapter.submitList(it)
        }
    }
}
