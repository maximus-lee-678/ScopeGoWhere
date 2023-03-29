package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentSample4AtpBinding
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample4AtpFragment : Fragment() {
    private lateinit var binding: FragmentSample4AtpBinding
    private val sampleViewModel by activityViewModels<SampleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSample4AtpBinding.inflate(inflater)

        sampleViewModel.sampleData.observe(viewLifecycleOwner) {
            if (it.waterATPRLU != null) binding.atpWaterRLUInput.setText(it.waterATPRLU.toString())
            if (it.swabATPRLU != null) binding.atpSwabRLUInput.setText(it.swabATPRLU.toString())
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        sampleViewModel.setSample4Atp(
            binding.atpWaterRLUInput.text.toString().toIntOrNull(),
            binding.atpSwabRLUInput.text.toString().toIntOrNull(),
        )
    }
}