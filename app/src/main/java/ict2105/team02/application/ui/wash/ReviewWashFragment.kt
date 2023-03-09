package ict2105.team02.application.ui.wash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentReviewWashBinding
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.viewmodel.WashViewModel

class ReviewWashFragment : Fragment() {
    private lateinit var binding: FragmentReviewWashBinding
    private lateinit var viewModel: WashViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(WashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Review"
        super.onViewCreated(view, savedInstanceState)
//        binding.brand.editText?.setText(viewModel.scopeBrand.toString())

        viewModel.washData.observe(viewLifecycleOwner){
            binding.apply {
                aerSerialNo.editText?.setText(it.AERModel, TextView.BufferType.EDITABLE)
            }
            binding.aerModel.editText?.setText(it?.AERModel)
            binding.aerSerialNo.editText?.setText(it.AERSerial?.toString())
            binding.detergentUsed.editText?.setText(it?.DetergentUsed)
            binding.detergentLotNo.editText?.setText(it?.DetergentLotNo!!.toString())
            binding.filterChangeDate.editText?.setText(it?.FilterChangeDate.toString())
            binding.disinfectantUsed.editText?.setText(it?.DisinfectantUsed)
            binding.disinfectantLotNo.editText?.setText(it?.DisinfectantLotNo!!.toString())
//            binding.disinfectantChanged.editText?.setText(it?.DisinfectantChangedDate.toString())
            binding.scopeDryer.editText?.setText(it?.ScopeDryer!!.toString())
            binding.dryerLevel.editText?.setText(it?.DryerLevel!!.toString())
            binding.remarks.editText?.setText(it?.Remarks)
        }

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input
            Log.d("TestHash",viewModel.convertWashDataToMap().toString())
            // if true set it to true
            // replace with last fragment
            viewModel.insertIntoDB(viewModel.ScopeSerial)
            val intent = Intent (activity, MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

}