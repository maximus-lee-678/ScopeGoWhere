package ict2105.team02.application.ui.sample

import android.R
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
            adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, listOf("False", "True"))
            setSelection(0)

            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val swabResult = parent!!.getItemAtPosition(position).toString().lowercase()
                    viewModel.setSample2Result(swabResult.toBooleanStrictOrNull())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample2Swab(
                binding.dateOfResultInput.text.toString().parseDateString(),
                binding.actionInput.text.toString(),
                binding.cultureCommentInput.text.toString()
            )
        }

        binding.dateOfResultInput.addTextChangedListener(textChangeListener)
        binding.actionInput.addTextChangedListener(textChangeListener)
        binding.cultureCommentInput.addTextChangedListener(textChangeListener)

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

    private fun validate(): Boolean {
        var valid = true

        if(TextUtils.isEmpty(binding.dateOfResultInput.text) ||
            TextUtils.isEmpty(binding.actionInput.text)){
            binding.errorMsgSwab.text = "Please fill in all the fields"
            valid = false
        }

        if (valid) {
            binding.errorMsgSwab.visibility = View.GONE
        } else {
            binding.errorMsgSwab.visibility = View.VISIBLE
        }

        return valid
    }
}