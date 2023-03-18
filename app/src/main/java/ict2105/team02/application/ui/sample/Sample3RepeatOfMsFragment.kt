package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample3RepeatOfMsBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.SampleViewModel
import java.util.*

class Sample3RepeatOfMsFragment : Fragment() {
    private lateinit var binding: FragmentSample3RepeatOfMsBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSample3RepeatOfMsBinding.inflate(inflater)
        activity?.title = "Sample Equipment (3/4)"

        // Set existing data, if any
        val sampleData = viewModel.sampleData.value
        if (sampleData != null) {
            if (sampleData.quarantineRequired != null) binding.quarantineRequiredDropDown.setText(sampleData.quarantineRequired.toString())
            if (sampleData.repeatDateMS != null) binding.repeatDate.setText(sampleData.repeatDateMS.toDateString())
            if (sampleData.borescope != null) binding.borescopeDropdown.setText(sampleData.borescope.toString())
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample3RepeatOfMS(
                binding.quarantineRequiredDropDown.text.toString().isNotEmpty(), // TODO: Replace input type
                binding.repeatDate.text.toString().parseDateString(),
                binding.borescopeDropdown.text.toString().isNotEmpty() // TODO: Replace input type
            )
        }
        binding.quarantineRequiredDropDown.addTextChangedListener(textChangeListener)
        binding.repeatDate.addTextChangedListener(textChangeListener)
        binding.borescopeDropdown.addTextChangedListener(textChangeListener)

        // Date picker
        binding.repeatDate.setOnClickListener {
            val datePicker = viewModel.datePicker.setTitleText("Select Date of Result").build()
            datePicker.show(childFragmentManager, "Date Picker")
            datePicker.addOnPositiveButtonClickListener {
                binding.repeatDate.setText(Date((it)).toDateString())
            }
        }

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(binding.quarantineRequiredDropDown.text) ||
            TextUtils.isEmpty(binding.repeatDate.text) ||
            TextUtils.isEmpty(binding.borescopeDropdown.text)
        ) {
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