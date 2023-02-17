package ict2105.team02.application.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.MainActivity
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentDisinfectantWashBinding
import ict2105.team02.application.databinding.FragmentScopeDetailsWashBinding
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
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
            viewModel.isDisinfectantDone.value = true

            // replace with last fragment
            val fragment = WashEquipmentFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
    }
}