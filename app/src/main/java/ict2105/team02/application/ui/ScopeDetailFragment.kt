package ict2105.team02.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ict2105.team02.application.databinding.FragmentScopeDetailBinding
import ict2105.team02.application.viewmodel.ScopeDetailViewModel

private const val KEY_ENDOSCOPE_SERIAL = "SN"

class ScopeDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScopeDetailBinding
    private lateinit var viewModel: ScopeDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScopeDetailBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(ScopeDetailViewModel::class.java)

        binding.scopeDetailCard.visibility = View.INVISIBLE

        binding.scanAgainButton.setOnClickListener {
            dismiss()
        }

        viewModel.scopeDetail.observe(this) {
            binding.serialTextView.text = it.serial
            binding.statusTextView.text = it.status
            binding.nextSampleTextView.text = it.nextSample.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serial = arguments?.getString(KEY_ENDOSCOPE_SERIAL)
        if (serial != null) {
            viewModel.fetchScopeDetail(serial) {
                requireActivity().runOnUiThread {
                    binding.loadScopeProgressIndicator.visibility = View.INVISIBLE
                    binding.scopeDetailCard.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(endoscopeSerialNo: String) = ScopeDetailFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ENDOSCOPE_SERIAL, endoscopeSerialNo)
            }
        }
    }
}