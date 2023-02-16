package ict2105.team02.application.equipment.wash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import androidx.databinding.DataBindingUtil
import ict2105.team02.application.MainActivity
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityWashEquipmentBinding
import ict2105.team02.application.databinding.FragmentScopeDetailsWashBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer


/**
 * A simple [Fragment] subclass.
 * Use the [ScopeDetailsWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScopeDetailsWashFragment : Fragment() {
    private lateinit var binding: FragmentScopeDetailsWashBinding
    private val sharedViewModel: WashViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScopeDetailsWashBinding.inflate(inflater)
//        binding.lifecycleOwner = this
//        binding.viewmodel = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener{
            sharedViewModel.done1.value = true
            val fragment = WashEquipmentFragment()
            (activity as MainActivity).navbarNavigate(fragment)
            // validate the input

            // toggle the checkedTextView
        }
    }
}