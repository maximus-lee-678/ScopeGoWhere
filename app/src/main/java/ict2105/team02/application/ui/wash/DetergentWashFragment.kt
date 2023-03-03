package ict2105.team02.application.ui.wash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentDetergentWashBinding
import ict2105.team02.application.viewmodel.WashViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DetergentWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetergentWashFragment : Fragment() {
    private lateinit var binding: FragmentDetergentWashBinding
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetergentWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(WashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getActivity()?.setTitle("Wash Equipment(3/5)")
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
            viewModel.washData.postValue(viewModel.washData.value?.copy(
                DetergentUsed = binding.detergentUsed.editText?.text.toString(),
                DetergentLotNo = binding.detergentLotNo.editText?.text.toString().toInt()
//                filterChangeDate = binding.filterChangeDate.editText?.text.toString().toDate()
            ))
            // replace with last fragment
            val fragment = DisinfectantWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}