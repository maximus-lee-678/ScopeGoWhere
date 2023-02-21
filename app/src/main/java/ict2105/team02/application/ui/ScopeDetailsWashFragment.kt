package ict2105.team02.application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ict2105.team02.application.MainActivity
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentScopeDetailsWashBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.WashActivity
import ict2105.team02.application.viewmodel.WashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ScopeDetailsWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScopeDetailsWashFragment : Fragment() {
    private lateinit var binding: FragmentScopeDetailsWashBinding
//    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentScopeDetailsWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(WashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getActivity()?.setTitle("Wash Equipment(1/5)")
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
            viewModel.scopeBrand.value = binding.brand.toString()
            viewModel.scopeModel.value = binding.model.toString()
            viewModel.scopeSerial.value = binding.serialNo.toString()
            // replace with last fragment
            val fragment = WasherWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}