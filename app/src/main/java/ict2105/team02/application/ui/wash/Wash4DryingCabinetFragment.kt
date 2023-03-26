package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash4DryingCabinetBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.WashViewModel

class Wash4DryingCabinetFragment : Fragment() {
    private lateinit var binding: FragmentWash4DryingCabinetBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash4DryingCabinetBinding.inflate(inflater)

        viewModel.washData.observe(viewLifecycleOwner) {
            if (it.ScopeDryer != null) binding.scopeDryer.setText(it.ScopeDryer.toString())
            if (it.DryerLevel != null) binding.dryerLevel.setText(it.DryerLevel.toString())
            if (it.Remarks != null) binding.remarks.setText(it.Remarks.toString())
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Save fields to ViewModel when leaving fragment
        viewModel.setWash4Drying(
            binding.scopeDryer.text.toString().toIntOrNull(),
            binding.dryerLevel.text.toString().toIntOrNull(),
            binding.remarks.text.toString()
        )
    }
}