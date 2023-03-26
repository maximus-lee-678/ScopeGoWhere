package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ict2105.team02.application.databinding.FragmentSample4AtpBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.toDateString
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

    private fun validate(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(binding.atpWaterRLUInput.text)
            || TextUtils.isEmpty(binding.atpSwabRLUInput.text)
        ) {
            binding.errorMsgAtp.text = "Please fill in all the fields"
            valid = false
        }

        if (valid) {
            binding.errorMsgAtp.visibility = View.GONE
        } else {
            binding.errorMsgAtp.visibility = View.VISIBLE
        }

        return valid
    }
}