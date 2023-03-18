package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash4DisinfectantBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.WashViewModel
import java.util.*

class Wash4DisinfectantFragment : Fragment() {
    private lateinit var binding: FragmentWash4DisinfectantBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash4DisinfectantBinding.inflate(inflater)
        activity?.title = "Wash Equipment (4/5)"

        // Set existing data, if any
        val washData = viewModel.washData.value
        if (washData != null) {
            if (washData.DisinfectantUsed != null) binding.disinfectantUsed.setText(washData.DisinfectantUsed)
            if (washData.DisinfectantLotNo != null) binding.disinfectantLotNo.setText(washData.DisinfectantLotNo.toString())
            if (washData.DisinfectantChangedDate != null) binding.disinfectantChanged.setText(washData.DisinfectantChangedDate.toDateString())
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            viewModel.setWash4Disinfectant(
                binding.disinfectantUsed.text.toString(),
                binding.disinfectantLotNo.text.toString().toIntOrNull(),
                binding.disinfectantChanged.text.toString().parseDateString()
            )
        }
        binding.disinfectantUsed.addTextChangedListener(textChangeListener)
        binding.disinfectantLotNo.addTextChangedListener(textChangeListener)
        binding.disinfectantChanged.addTextChangedListener(textChangeListener)

        // Date picker
        binding.disinfectantChanged.setOnClickListener {
            val datePicker = viewModel.datePicker
                .setTitleText("Date of Disinfectant Change")
                .build()
            datePicker.show(childFragmentManager, "Disinfectant")
            datePicker.addOnPositiveButtonClickListener {
                binding.disinfectantChanged.setText(Date(it).toDateString())
            }
        }

        return binding.root
    }
}