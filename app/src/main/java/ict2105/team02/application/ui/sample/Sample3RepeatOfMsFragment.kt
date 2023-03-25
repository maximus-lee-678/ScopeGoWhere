package ict2105.team02.application.ui.sample

import android.R
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample3RepeatOfMsBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.SampleViewModel
import java.util.*

class Sample3RepeatOfMsFragment : Fragment() {
    private lateinit var binding: FragmentSample3RepeatOfMsBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSample3RepeatOfMsBinding.inflate(inflater)
        binding.quarantineLayout.visibility = View.GONE
        val quarantineSpinner = binding.quarantineSpinner
        val borescopeSpinner = binding.borescopeSpinner
        val optionList = listOf("False", "True")
        var quarantineResult = "false"
        var borescope = "false"
        quarantineSpinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, optionList)
        quarantineSpinner.setSelection(0)
        borescopeSpinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, optionList)
        borescopeSpinner.setSelection(0)
        // Set existing data, if any
        val sampleData = viewModel.sampleData.value
        if (sampleData != null) {
            if (sampleData.quarantineRequired != null) {
                if (sampleData.quarantineRequired) {
                    quarantineSpinner.setSelection(1)
                } else {
                    quarantineSpinner.setSelection(0)
                }
            }
            if (sampleData.repeatDateMS != null) binding.repeatDate.setText(sampleData.repeatDateMS.toDateString())
            if (sampleData.borescope != null) {
                if (sampleData.borescope) {
                    borescopeSpinner.setSelection(1)
                } else {
                    borescopeSpinner.setSelection(0)
                }
            }
        }
        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample3RepeatOfMS(
                binding.repeatDate.text.toString().parseDateString()
            )
        }
        binding.repeatDate.addTextChangedListener(textChangeListener)
        borescopeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                borescope = parent!!.getItemAtPosition(position).toString().toLowerCase()
                viewModel.setSample3Data(quarantineResult.toBooleanStrictOrNull()!!,borescope.toBooleanStrictOrNull()!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do Nothing
            }
        }
        quarantineSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                quarantineResult = parent!!.getItemAtPosition(position).toString().toLowerCase()
                viewModel.setSample3Data(quarantineResult.toBooleanStrictOrNull()!!,borescope.toBooleanStrictOrNull()!!)
                if(position == 1){
                    binding.quarantineLayout.visibility = View.VISIBLE
                } else{
                    binding.quarantineLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do Nothing
            }
        }
        // Date picker
        binding.repeatDate.setOnClickListener {
            Utils.createMaterialFutureDatePicker("Select date of resample", {
                binding.repeatDate.setText("") // Set empty string if cancelled (this field is optional if result is negative)
                 }, { epoch ->
                    binding.repeatDate.setText(Date(epoch).toDateString())
                 }
            ).show(childFragmentManager, null)
        }
        return binding.root
    }
    fun validate(): Boolean {
        var valid = true
        if (TextUtils.isEmpty(binding.repeatDate.text)) {
            binding.errorMsgRepeat.text = "Please fill in all the fields"
            valid = false
        }
        if (valid) {
            binding.errorMsgRepeat.visibility = View.GONE
        } else {
            binding.errorMsgRepeat.visibility = View.VISIBLE
        }
        return valid
    }
}