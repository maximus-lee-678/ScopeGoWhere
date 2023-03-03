package ict2105.team02.application.ui.wash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentDryingCabinetWashBinding
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
//            viewModel.isDryingCabinetDone.value = true
//            viewModel.scopeDryer.value = binding.scopeDryer.editText?.text.toString()
//            viewModel.scopeLevel.value = binding.dryerLevel.editText?.text.toString()
//            viewModel.remarks.value = binding.remarks.editText?.text.toString()
            viewModel.washData.postValue(viewModel.washData.value?.copy(
                ScopeDryer = binding.scopeDryer.editText?.text.toString().toInt(),
                DryerLevel = binding.dryerLevel.editText?.text.toString().toInt(),
                Remarks = binding.remarks.editText?.text.toString()))

            // replace with last fragment
            val fragment = ReviewWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}