package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample1FluidResultBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.SampleViewModel
import java.util.*

class Sample1FluidResultFragment : Fragment() {
    private lateinit var binding: FragmentSample1FluidResultBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = FragmentSample1FluidResultBinding.inflate(inflater)

        // Set existing data, if any
        val sampleData = viewModel.sampleData.value
        if (sampleData != null) {
            if (sampleData.resultDate != null) binding.dateOfResultInput.setText(sampleData.resultDate.toDateString())
            if (sampleData.fluidResult != null) binding.fluidResultInput.setText(sampleData.fluidResult.toString())
            if (sampleData.fluidAction != null) binding.actionInput.setText(sampleData.fluidAction)
            if (sampleData.fluidComment != null) binding.cultureCommentInput.setText(sampleData.fluidComment)
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample1Fluid(
                binding.dateOfResultInput.text.toString().parseDateString(),
                binding.fluidResultInput.text.toString().isNotEmpty(),
                binding.actionInput.text.toString(),
                binding.cultureCommentInput.text.toString()
            )
        }
        binding.dateOfResultInput.addTextChangedListener(textChangeListener)
        binding.fluidResultInput.addTextChangedListener(textChangeListener)
        binding.actionInput.addTextChangedListener(textChangeListener)
        binding.cultureCommentInput.addTextChangedListener(textChangeListener)

        // Date picker
        binding.dateOfResultInput.setOnClickListener{
            Utils.createMaterialDatePicker("Select date of sample result") { epoch ->
                binding.dateOfResultInput.setText(Date(epoch).toDateString())
            }.show(childFragmentManager, null)
        }

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = true

        if(TextUtils.isEmpty(binding.dateOfResultInput.text) ||
            TextUtils.isEmpty(binding.fluidResultInput.text)||
            TextUtils.isEmpty(binding.actionInput.text)){
            binding.errorMsgFluid.text = "Please fill in all the fields"
            valid = false
        }

        if (valid) {
            binding.errorMsgFluid.visibility = View.GONE
        } else {
            binding.errorMsgFluid.visibility = View.VISIBLE
        }

        return valid
    }
}