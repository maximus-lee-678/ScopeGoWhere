package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash2DetergentBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.WashViewModel
import java.util.*

class Wash2DetergentFragment : Fragment() {
    private lateinit var binding: FragmentWash2DetergentBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWash2DetergentBinding.inflate(inflater)

        // Set existing data, if any
        val washData = viewModel.washData.value
        if (washData != null) {
            if (washData.DetergentUsed != null) binding.detergentUsed.setText(washData.DetergentUsed)
            if (washData.DetergentLotNo != null) binding.detergentLotNo.setText(washData.DetergentLotNo.toString())
            if (washData.FilterChangeDate != null) binding.filterChangeDate.setText(washData.FilterChangeDate.toDateString())
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            viewModel.setWash2Detergent(
                binding.detergentUsed.text.toString(),
                binding.detergentLotNo.text.toString().toIntOrNull(),
                binding.filterChangeDate.text.toString().parseDateString()
            )
        }
        binding.detergentUsed.addTextChangedListener(textChangeListener)
        binding.detergentLotNo.addTextChangedListener(textChangeListener)
        binding.filterChangeDate.addTextChangedListener(textChangeListener)

        // Date picker
        binding.filterChangeDate.setOnClickListener {
            Utils.createMaterialDatePicker("Select filter changed date") {
                binding.filterChangeDate.setText(Date((it)).toDateString())
            }.show(childFragmentManager, null)
        }

        return binding.root
    }
}