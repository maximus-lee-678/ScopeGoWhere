package ict2105.team02.application.ui.sample

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentAtpSampleBinding
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.ui.wash.WasherWashFragment
import ict2105.team02.application.viewmodel.SampleViewModel

class AtpSampleFragment : Fragment() {
    private lateinit var binding : FragmentAtpSampleBinding
    private lateinit var viewModel : SampleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAtpSampleBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(SampleViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getActivity()?.setTitle("Sample Equipment(4/4)")
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
//            viewModel.scopeBrand.value = binding.brand.toString()
//            viewModel.scopeModel.value = binding.model.toString()
//            viewModel.scopeSerial.value = binding.serialNo.toString()
            // replace with last fragment
            val intent = Intent (getActivity(), MainActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}