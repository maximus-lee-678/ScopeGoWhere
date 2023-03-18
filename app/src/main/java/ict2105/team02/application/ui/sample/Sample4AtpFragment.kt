package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample4AtpBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample4AtpFragment : Fragment() {
    private lateinit var binding: FragmentSample4AtpBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSample4AtpBinding.inflate(inflater)
        activity?.title = "Sample Equipment (4/4)"

        // Set existing data, if any
        val sampleData = viewModel.sampleData.value
        if (sampleData != null) {
            if (sampleData.waterATPRLU != null) binding.atpWaterRLUInput.setText(sampleData.waterATPRLU.toString())
            if (sampleData.swabATPRLU != null) binding.atpSwabRLUInput.setText(sampleData.swabATPRLU.toString())
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample4Atp(
                binding.atpWaterRLUInput.text.toString().toInt(),
                binding.atpSwabRLUInput.text.toString().toInt(),
            )
        }
        binding.atpWaterRLUInput.addTextChangedListener(textChangeListener)
        binding.atpSwabRLUInput.addTextChangedListener(textChangeListener)

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(binding.atpWaterRLUInput.text)
            || TextUtils.isEmpty(binding.atpSwabRLUInput.text)
        ) {
            binding.errorMsgAtp.text = "Please fill in all the fields"
            valid = false
        }

        if (valid) {
            binding.errorMsgAtp.visibility = View.GONE
        } else {
            binding.errorMsgAtp.visibility = View.VISIBLE
        }

        return valid
    }
}