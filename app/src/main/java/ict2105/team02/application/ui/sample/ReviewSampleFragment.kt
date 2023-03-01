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
import ict2105.team02.application.databinding.FragmentReviewSampleBinding
import ict2105.team02.application.databinding.FragmentReviewWashBinding
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.viewmodel.SampleViewModel
import ict2105.team02.application.viewmodel.WashViewModel

class ReviewSampleFragment : Fragment() {
    private lateinit var binding: FragmentReviewSampleBinding
    private lateinit var viewModel: SampleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewSampleBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(SampleViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.setTitle("Review")
        super.onViewCreated(view, savedInstanceState)
//        binding.brand.editText?.setText(viewModel.scopeBrand.toString())

        viewModel.sampleData.observe(viewLifecycleOwner){
            binding.dateOfFluidResult.editText?.setText(it?.dateOfFluidResult)
            binding.fluidResult.editText?.setText(it?.fluidResult)
            binding.actionFluid.editText?.setText(it?.actionFluid)
            binding.cultureCommentFluid.editText?.setText(it?.cultureCommentFluid)
            binding.dateOfSwabResult.editText?.setText(it?.dateOfSwabResult)
            binding.swabResult.editText?.setText(it?.swabResult)
            binding.actionSwab.editText?.setText(it?.actionSwab)
            binding.cultureCommentSwab.editText?.setText(it?.cultureCommentSwab)
            binding.quarantinePeriodInput.editText?.setText(it?.quarantinePeriod)
            binding.repeatDateMsInput.editText?.setText(it?.repeatDateMS)
            binding.borescopeDropdown.editText?.setText(it?.borescope)
            binding.atpWaterRluInput.editText?.setText(it?.atpWaterRLU)
            binding.atpSwapRluInput.editText?.setText(it?.atpSwapRLU)
        }

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input

            // if true set it to true
            val intent = Intent (getActivity(), MainActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}