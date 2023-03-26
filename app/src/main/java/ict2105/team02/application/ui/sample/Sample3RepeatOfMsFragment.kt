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
import androidx.fragment.app.viewModels
import ict2105.team02.application.databinding.FragmentSample3RepeatOfMsBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.SampleViewModel
import java.util.*

class Sample3RepeatOfMsFragment : Fragment() {
    private lateinit var binding: FragmentSample3RepeatOfMsBinding
    private val sampleViewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSample3RepeatOfMsBinding.inflate(inflater)
        binding.quarantineLayout.visibility = View.GONE

        val optionList = listOf("False", "True")

        binding.quarantineSpinner.apply {
            adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, optionList)
            setSelection(0)

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val quarantineResult = parent!!.getItemAtPosition(position).toString().lowercase()
                    sampleViewModel.setSample3Data(quarantineResult.toBooleanStrictOrNull(), quarantineResult.toBooleanStrictOrNull())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { // Do Nothing }
                }
            }
        }

        binding.borescopeSpinner.apply {
            adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, optionList)
            setSelection(0)

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val isBorescope = parent!!.getItemAtPosition(position).toString().lowercase()
                    sampleViewModel.setSample3Data(sampleViewModel.sampleData.value!!.quarantineRequired, isBorescope.toBooleanStrictOrNull()!!)
                    if (position == 1) {
                        binding.quarantineLayout.visibility = View.VISIBLE
                    } else {
                        binding.quarantineLayout.visibility = View.GONE
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { // Do Nothing }
                }
            }
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            sampleViewModel.setSample3RepeatOfMS(
                binding.repeatDate.text.toString().parseDateString()
            )
        }
        binding.repeatDate.addTextChangedListener(textChangeListener)
        // Date picker
        binding.repeatDate.setOnClickListener {
            Utils.createMaterialFutureDatePicker("Select date of resample", {
                binding.repeatDate.setText("") // Set empty string if cancelled (this field is optional if result is negative)
            }, { epoch ->
                binding.repeatDate.setText(Date(epoch).toDateString())
            }
            ).show(childFragmentManager, null)
        }

        sampleViewModel.sampleData.observe(viewLifecycleOwner) {
            if (it.quarantineRequired != null) {
                if (it.quarantineRequired) {
                    binding.quarantineSpinner.setSelection(1)
                } else {
                    binding.quarantineSpinner.setSelection(0)
                }
            }
            if (it.repeatDateMS != null) binding.repeatDate.setText(it.repeatDateMS.toDateString())
            if (it.borescope != null) {
                if (it.borescope) {
                    binding.borescopeSpinner.setSelection(1)
                } else {
                    binding.borescopeSpinner.setSelection(0)
                }
            }
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