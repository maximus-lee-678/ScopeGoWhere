package ict2105.team02.application.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.MainActivity
import ict2105.team02.application.R
import ict2105.team02.application.WashActivity
import ict2105.team02.application.databinding.FragmentDryingCabinetWashBinding
import ict2105.team02.application.databinding.FragmentScopeDetailsWashBinding
import ict2105.team02.application.viewmodel.WashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DryingCabinetWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DryingCabinetWashFragment : Fragment() {
    private lateinit var binding: FragmentDryingCabinetWashBinding
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDryingCabinetWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(WashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getActivity()?.setTitle("Wash Equipment(5/5)")
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
            viewModel.isDryingCabinetDone.value = true
            viewModel.scopeDryer.value = binding.scopeDryer.toString()
            viewModel.scopeLevel.value = binding.dryerLevel.toString()
            viewModel.remarks.value = binding.remarks.toString()

            // replace with last fragment
            val intent = Intent (getActivity(), MainActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}