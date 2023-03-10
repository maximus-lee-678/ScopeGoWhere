package ict2105.team02.application.ui.wash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentWasherWashBinding
import ict2105.team02.application.viewmodel.WashViewModel

class WasherWashFragment : Fragment() {
    private lateinit var binding: FragmentWasherWashBinding
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWasherWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[WashViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getActivity()?.setTitle("Wash Equipment(2/5)")
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
            viewModel.washData.postValue(viewModel.washData.value?.copy(
                AERModel = binding.aerModel.editText?.text.toString(),
                AERSerial = binding.aerSerialNo.editText?.text.toString().toInt()))


            // replace with last fragment
            val fragment = DetergentWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }

}