package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample2SwabResultBinding
import ict2105.team02.application.utils.*
import ict2105.team02.application.viewmodel.SampleViewModel
import java.time.LocalDate
import java.util.*

class Sample2SwabResultFragment : Fragment() {
    private lateinit var binding: FragmentSample2SwabResultBinding
    private val viewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSample2SwabResultBinding.inflate(inflater)
        activity?.title = "Sample Equipment (2/4)"

        // Set existing data, if any
        val sampleData = viewModel.sampleData.value
        if (sampleData != null) {
            if (sampleData.swabDate != null) binding.dateOfResultInput.setText(sampleData.swabDate.toDateString())
            if (sampleData.swabResult != null) binding.swabResultInput.setText(sampleData.swabResult.toString())
            if (sampleData.swabAction != null) binding.actionInput.setText(sampleData.swabAction)
            if (sampleData.swabCultureComment != null) binding.cultureCommentInput.setText(sampleData.swabCultureComment)
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setSample2Swab(
                binding.dateOfResultInput.text.toString().parseDateString(),
                binding.swabResultInput.text.toString().isNotEmpty(), // TODO: Replace input type
                binding.actionInput.text.toString(),
                binding.cultureCommentInput.text.toString()
            )
        }
        binding.dateOfResultInput.addTextChangedListener(textChangeListener)
        binding.swabResultInput.addTextChangedListener(textChangeListener)
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

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "RESUME")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "PAUSE")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "START")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "STOP")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "DESTROY")
    }

    private fun validate(): Boolean {
        var valid = true

        if(TextUtils.isEmpty(binding.dateOfResultInput.text) ||
            TextUtils.isEmpty(binding.swabResultInput.text)||
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