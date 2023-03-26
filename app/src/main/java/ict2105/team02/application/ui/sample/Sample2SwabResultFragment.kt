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
import ict2105.team02.application.databinding.FragmentSample2SwabResultBinding
import ict2105.team02.application.utils.*
import ict2105.team02.application.viewmodel.SampleViewModel
import java.util.*

class Sample2SwabResultFragment : Fragment() {
    private lateinit var binding: FragmentSample2SwabResultBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSample2SwabResultBinding.inflate(inflater)

        binding.swabResultSpinner.apply {
            adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_positive_negative, android.R.layout.simple_spinner_dropdown_item)
            setSelection(0)

            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val swabResult = parent!!.getItemAtPosition(position).toString()
                    viewModel.setSample2Result(swabResult.mapPositiveNegativeToBoolean())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }
        }

        // Date picker
        binding.dateOfResultInput.setOnClickListener{
            Utils.createMaterialPastDatePicker("Select date of sample result") { epoch ->
                binding.dateOfResultInput.setText(Date(epoch).toDateString())
            }.show(childFragmentManager, null)
        }

        viewModel.sampleData.observe(viewLifecycleOwner) {
            if (it.swabDate != null) binding.dateOfResultInput.setText(it.swabDate.toDateString())
            if (it.swabResult != null) {
                if (it.swabResult) {
                    binding.swabResultSpinner.setSelection(1)
                } else {
                    binding.swabResultSpinner.setSelection(0)
                }
            }
            if (it.swabAction != null) binding.actionInput.setText(it.swabAction)
            if (it.swabCultureComment != null) binding.cultureCommentInput.setText(it.swabCultureComment)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Save fields to ViewModel when leaving fragment
        viewModel.setSample2Swab(
            binding.dateOfResultInput.text.toString().parseDateString(),
            binding.actionInput.text.toString(),
            binding.cultureCommentInput.text.toString()
        )
    }
}