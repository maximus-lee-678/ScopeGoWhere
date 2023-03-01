package ict2105.team02.application.ui.wash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentDisinfectantWashBinding
import ict2105.team02.application.viewmodel.WashViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DisinfectantWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisinfectantWashFragment : Fragment() {
    private lateinit var binding: FragmentDisinfectantWashBinding
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDisinfectantWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(WashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getActivity()?.setTitle("Wash Equipment(4/5)")
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
//            viewModel.isDisinfectantDone.value = true
//            viewModel.disinfectantUsed.value = binding.disinfectantUsed.editText?.text.toString()
//            viewModel.disinfectantLotNo.value = binding.disinfectantLotNo.editText?.text.toString()
//            viewModel.disinfectantChanged.value = binding.disinfectantChanged.editText?.text.toString()
            viewModel.washData.postValue(viewModel.washData.value?.copy(
                disinfectantUsed = binding.disinfectantUsed.editText?.text.toString(),
                disinfectantLotNo = binding.disinfectantLotNo.editText?.text.toString(),
                disinfectantChanged = binding.disinfectantChanged.editText?.text.toString()))

            // replace with last fragment
            val fragment = DryingCabinetWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}