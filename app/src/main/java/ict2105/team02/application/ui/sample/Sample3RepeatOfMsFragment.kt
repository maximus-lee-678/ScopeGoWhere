package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentSample3RepeatOfMsBinding
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.mapYesNoToBoolean
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

        binding.repeatDate.visibility = View.GONE

        binding.quarantineSpinner.apply {
            adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_yes_no, android.R.layout.simple_spinner_dropdown_item)
            setSelection(0)

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val quarantineResult = parent!!.getItemAtPosition(position).toString()
                    sampleViewModel.setSample3Data(quarantineResult.mapYesNoToBoolean(), sampleViewModel.sampleData.value?.borescope)

                    // Show next sample date if result is positive
                    when (position) {
                        0 -> binding.repeatDate.visibility = View.GONE
                        1 -> binding.repeatDate.visibility = View.VISIBLE
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }
        }

        binding.borescopeSpinner.apply {
            adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_yes_no, android.R.layout.simple_spinner_dropdown_item)
            setSelection(0)

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val isBorescope = parent!!.getItemAtPosition(position).toString()
                    sampleViewModel.setSample3Data(sampleViewModel.sampleData.value?.quarantineRequired, isBorescope.mapYesNoToBoolean())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
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

    override fun onPause() {
        super.onPause()
        // Save fields to ViewModel when leaving fragment
        sampleViewModel.setSample3RepeatOfMS(
            binding.repeatDate.text.toString().parseDateString()
        )
    }
}