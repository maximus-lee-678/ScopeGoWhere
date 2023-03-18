package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash2WasherBinding
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.viewmodel.WashViewModel

class Wash2WasherFragment : Fragment() {
    private lateinit var binding: FragmentWash2WasherBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash2WasherBinding.inflate(inflater)
        activity?.title = "Wash Equipment (2/5)"

        // Set existing data, if any
        val washData = viewModel.washData.value
        if (washData != null) {
            Log.d(TAG, washData.toString())
            if (washData.AERModel != null) binding.editTextAERModel.setText(washData.AERModel)
            if (washData.AERSerial != null) binding.editTextAERSerial.setText(washData.AERSerial.toString())
        }

        // For validation and update view model
        val textChangeListener = TextChangeListener {
            validate()
            viewModel.setWash2AER(
                binding.editTextAERModel.text.toString(),
                binding.editTextAERSerial.text.toString().toIntOrNull()
            )
        }

        binding.editTextAERModel.addTextChangedListener(textChangeListener)
        binding.editTextAERSerial.addTextChangedListener(textChangeListener)

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(binding.editTextAERModel.text) || TextUtils.isEmpty(binding.editTextAERSerial.text)) {
            binding.errorMsg.text = "Please fill in all the fields"
            valid = false
        }
        else if (!binding.editTextAERSerial.text.toString().isDigitsOnly()) {
            binding.errorMsg.text = "AER Serial No. must only contain numbers"
            valid = false
        }

        if (valid) {
            binding.errorMsg.visibility = View.GONE
        } else {
            binding.errorMsg.visibility = View.VISIBLE
        }

        return valid
    }
}