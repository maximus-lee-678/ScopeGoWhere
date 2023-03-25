package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample1FluidResultBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample1FluidResultFragment : Fragment() {
    private lateinit var binding: FragmentSample1FluidResultBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = FragmentSample1FluidResultBinding.inflate(inflater)
        val spinner = binding.fluidResultInputSpinner
        val optionList = listOf("False", "True")
        var fluidResult = "false"
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, optionList)
        spinner.setSelection(0)
        // Set existing data, if any
        val sampleData = viewModel.sampleData.value
        if (sampleData != null) {
            if (sampleData.fluidResult != null){
                if(sampleData.fluidResult){
                    spinner.setSelection(1)
                }
                else {
                    spinner.setSelection(0)
                }
            }
            if (sampleData.fluidAction != null) binding.actionInput.setText(sampleData.fluidAction)
            if (sampleData.fluidComment != null) binding.cultureCommentInput.setText(sampleData.fluidComment)
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample1Fluid(
                binding.actionInput.text.toString(),
                binding.cultureCommentInput.text.toString()
            )
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fluidResult = parent!!.getItemAtPosition(position).toString().toLowerCase()
                viewModel.setSample1Result(fluidResult.toBooleanStrictOrNull()!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        binding.actionInput.addTextChangedListener(textChangeListener)
        binding.cultureCommentInput.addTextChangedListener(textChangeListener)

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = true

        if(TextUtils.isEmpty(binding.actionInput.text)){
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