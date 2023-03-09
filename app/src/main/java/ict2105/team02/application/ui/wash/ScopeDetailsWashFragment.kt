package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentScopeDetailsWashBinding
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.model.WashData
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import ict2105.team02.application.viewmodel.WashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ScopeDetailsWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScopeDetailsWashFragment : Fragment() {
    private lateinit var binding: FragmentScopeDetailsWashBinding
    private lateinit var viewModel: WashViewModel
    private lateinit var scopeDetailsMap: HashMap<String, Any>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScopeDetailsWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[WashViewModel::class.java]
        scopeDetailsMap = arguments?.getSerializable("scopeDetails") as HashMap<String, Any>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = "Wash Equipment(1/5)"
        super.onViewCreated(view, savedInstanceState)
//        viewModel.washData.value = WashData("","","","","","","","","",)
        binding.brand.editText?.setText(scopeDetailsMap["scopeBrand"].toString(), TextView.BufferType.EDITABLE)
        binding.model.editText?.setText(scopeDetailsMap["scopeModel"].toString(), TextView.BufferType.EDITABLE)
        binding.serialNo.editText?.setText(scopeDetailsMap["scopeSerial"].toString(), TextView.BufferType.EDITABLE)
        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input
            viewModel.ScopeSerial = scopeDetailsMap["scopeSerial"].toString()
            // if true set it to true

            // replace with last fragment
            val fragment = WasherWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}