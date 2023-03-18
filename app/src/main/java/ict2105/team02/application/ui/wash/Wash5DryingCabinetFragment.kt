package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash5DryingCabinetBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.viewmodel.WashViewModel

class Wash5DryingCabinetFragment : Fragment() {
    private lateinit var binding: FragmentWash5DryingCabinetBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash5DryingCabinetBinding.inflate(inflater)
        activity?.title = "Wash Equipment (5/5)"

        // Set existing data, if any
        val washData = viewModel.washData.value
        if (washData != null) {
            if (washData.ScopeDryer != null) binding.scopeDryer.setText(washData.ScopeDryer.toString())
            if (washData.DryerLevel != null) binding.dryerLevel.setText(washData.DryerLevel.toString())
            if (washData.Remarks != null) binding.remarks.setText(washData.Remarks.toString())
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            viewModel.setWash5Drying(
                binding.scopeDryer.text.toString().toIntOrNull(),
                binding.dryerLevel.text.toString().toIntOrNull(),
                binding.remarks.text.toString()
            )
        }
        binding.scopeDryer.addTextChangedListener(textChangeListener)
        binding.dryerLevel.addTextChangedListener(textChangeListener)
        binding.remarks.addTextChangedListener(textChangeListener)

        return binding.root
    }
}