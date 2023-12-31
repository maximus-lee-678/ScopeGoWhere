package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash2DetergentBinding
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.WashViewModel
import java.util.*

class Wash2DetergentFragment : Fragment() {
    private lateinit var binding: FragmentWash2DetergentBinding
    private val washViewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWash2DetergentBinding.inflate(inflater)

        // Date picker
        binding.filterChangeDate.setOnClickListener {
            Utils.createMaterialPastDatePicker("Select filter changed date") {
                binding.filterChangeDate.setText(Date((it)).toDateString())
            }.show(childFragmentManager, null)
        }

        washViewModel.washData.observe(viewLifecycleOwner) {
            if (it.DetergentUsed != null) binding.detergentUsed.setText(it.DetergentUsed)
            if (it.DetergentLotNo != null) binding.detergentLotNo.setText(it.DetergentLotNo.toString())
            if (it.FilterChangeDate != null) binding.filterChangeDate.setText(it.FilterChangeDate.toDateString())
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Save fields to ViewModel when leaving fragment
        washViewModel.setWash2Detergent(
            binding.detergentUsed.text.toString(),
            binding.detergentLotNo.text.toString().toIntOrNull(),
            binding.filterChangeDate.text.toString().parseDateString()
        )
    }
}