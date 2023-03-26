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
import ict2105.team02.application.databinding.FragmentWash1WasherBinding
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.viewmodel.WashViewModel

class Wash1WasherFragment : Fragment() {
    private lateinit var binding: FragmentWash1WasherBinding
    private val washViewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash1WasherBinding.inflate(inflater)

        washViewModel.washData.observe(viewLifecycleOwner) {
            if (it.AERModel != null) binding.editTextAERModel.setText(it.AERModel)
            if (it.AERSerial != null) binding.editTextAERSerial.setText(it.AERSerial.toString())
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Save fields to ViewModel when leaving fragment
        washViewModel.setWash1AER(
            binding.editTextAERModel.text.toString(),
            binding.editTextAERSerial.text.toString().toIntOrNull()
        )
    }
}