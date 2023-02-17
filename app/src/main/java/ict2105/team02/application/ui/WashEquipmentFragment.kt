package ict2105.team02.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.HomeFragment
import ict2105.team02.application.MainActivity
import ict2105.team02.application.databinding.ActivityWashEquipmentBinding
import ict2105.team02.application.viewmodel.WashViewModel

class WashEquipmentFragment : Fragment() {
    private lateinit var binding: ActivityWashEquipmentBinding
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityWashEquipmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(WashViewModel::class.java)

        viewModel.isScopeDetailsDone.observe(viewLifecycleOwner) {
                binding.scopeDetaislCheckedTextView.isChecked = it
        }
        viewModel.isWasherDone.observe(viewLifecycleOwner) {
            binding.washerCheckedTextView.isChecked = it
        }
        viewModel.isDisinfectantDone.observe(viewLifecycleOwner) {
            binding.disinfectantCheckedTextView.isChecked = it
        }
        viewModel.isDetergentDone.observe(viewLifecycleOwner) {
            binding.detergentCheckedTextView.isChecked = it
        }
        viewModel.isDryingCabinetDone.observe(viewLifecycleOwner) {
            binding.dryingCabinetCheckedTextView.isChecked = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scopeDetaislCheckedTextView.setOnClickListener{
            val fragment = ScopeDetailsWashFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
        binding.washerCheckedTextView.setOnClickListener{
            val fragment = WasherWashFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
        binding.disinfectantCheckedTextView.setOnClickListener{
            val fragment = DisinfectantWashFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
        binding.detergentCheckedTextView.setOnClickListener{
            val fragment = DetergentWashFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
        binding.dryingCabinetCheckedTextView.setOnClickListener{
            val fragment = DryingCabinetWashFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
        binding.button3.setOnClickListener{
            val fragment = TodaySchedule()
            (activity as MainActivity).navbarNavigate(fragment)
            viewModel.isDryingCabinetDone.value = false
            viewModel.isWasherDone.value = false
            viewModel.isDetergentDone.value = false
            viewModel.isScopeDetailsDone.value = false
            viewModel.isDisinfectantDone.value = false
        }
    }
}