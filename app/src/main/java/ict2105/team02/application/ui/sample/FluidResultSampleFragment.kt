package ict2105.team02.application.ui.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.databinding.FragmentFluidResultSampleBinding
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.viewmodel.SampleViewModel

class FluidResultSampleFragment : Fragment() {
    private lateinit var binding: FragmentFluidResultSampleBinding
    private lateinit var viewModel: SampleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFluidResultSampleBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[SampleViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = "Update Result(1/4)"
        super.onViewCreated(view, savedInstanceState)

//        val button: Button = view.findViewById(R.id.button) as Button
        binding.button.setOnClickListener{
            // validate the input

            // if true update the viewModel
            //viewModel.dateOfResult = binding
            //viewModel.fluidResult = binding
            //viewModel.action = binding
            //viewModel.cultureComment = binding
            viewModel.sampleData.postValue(viewModel.sampleData.value?.copy(
                dateOfFluidResult = binding.dateOfResultInput.editText?.text.toString(),
                fluidResult = binding.fluidResultInput.editText?.text.toString(),
                actionFluid = binding.actionInput.editText?.text.toString(),
                cultureCommentFluid = binding.cultureCommentInput.editText?.text.toString()))

            val fragment = SwabResultSampleFragment()
            (activity as SampleActivity).navbarNavigate(fragment)
        }
    }
}