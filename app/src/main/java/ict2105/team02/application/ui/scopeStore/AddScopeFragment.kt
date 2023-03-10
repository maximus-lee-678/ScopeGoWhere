package ict2105.team02.application.ui.scopeStore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ict2105.team02.application.databinding.FragmentAddScopeBinding
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.NewScopeViewModel
import kotlinx.coroutines.launch
import java.util.Date

/**
 * A simple [Fragment] subclass.
 * Use the [AddScopeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddScopeFragment : Fragment() {
    private lateinit var binding: FragmentAddScopeBinding
    private lateinit var viewModel: NewScopeViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddScopeBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[NewScopeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddScope.setOnClickListener {
            val brand = binding.scopeBrand.editText?.text.toString()
            val model = binding.scopeModel.editText?.text.toString()
            val serial = binding.scopeSerial.editText?.text.toString().toInt()
            val type = binding.scopeType.editText?.text.toString()
            val nextSample:Date = Utils.strToDate(binding.nextSampleDate.editText?.text.toString())
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.insertScope(brand,model,serial,type,nextSample)
            }
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}