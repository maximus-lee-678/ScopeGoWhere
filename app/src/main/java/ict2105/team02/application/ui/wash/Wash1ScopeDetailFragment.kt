package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.databinding.FragmentWash1ScopeDetailsBinding
import ict2105.team02.application.viewmodel.WashViewModel

class Wash1ScopeDetailFragment : Fragment() {
    private lateinit var binding: FragmentWash1ScopeDetailsBinding

    private val viewModel by activityViewModels<WashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWash1ScopeDetailsBinding.inflate(inflater)
        activity?.title = "Wash Equipment (1/5)"
        
        viewModel.scopeData.observe(viewLifecycleOwner) {
            binding.brand.editText?.setText(it.scopeBrand)
            binding.model.editText?.setText(it.scopeModel)
            binding.serialNo.editText?.setText(it.scopeSerial.toString())
        }

        return binding.root
    }
}