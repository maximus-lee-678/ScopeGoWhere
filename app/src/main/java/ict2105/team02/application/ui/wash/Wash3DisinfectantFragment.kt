package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash3DisinfectantBinding
import ict2105.team02.application.utils.TextChangeListener
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.WashViewModel
import java.util.*

class Wash3DisinfectantFragment : Fragment() {
    private lateinit var binding: FragmentWash3DisinfectantBinding
    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWash3DisinfectantBinding.inflate(inflater)

        // Date picker
        binding.disinfectantChanged.setOnClickListener {
            Utils.createMaterialDatePicker("Select disinfectant changed date") {
                binding.disinfectantChanged.setText(Date(it).toDateString())
            }.show(childFragmentManager, null)
        }

        viewModel.washData.observe(viewLifecycleOwner) {
            if (it.DisinfectantUsed != null) binding.disinfectantUsed.setText(it.DisinfectantUsed)
            if (it.DisinfectantLotNo != null) binding.disinfectantLotNo.setText(it.DisinfectantLotNo.toString())
            if (it.DisinfectantChangedDate != null) binding.disinfectantChanged.setText(it.DisinfectantChangedDate.toDateString())
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Save fields to ViewModel when leaving fragment
        viewModel.setWash3Disinfectant(
            binding.disinfectantUsed.text.toString(),
            binding.disinfectantLotNo.text.toString().toIntOrNull(),
            binding.disinfectantChanged.text.toString().parseDateString()
        )
    }
}