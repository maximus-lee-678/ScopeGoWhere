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
import com.google.firebase.Timestamp
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentReviewWashBinding
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.viewmodel.WashViewModel
import java.text.SimpleDateFormat
import java.util.*

class ReviewWashFragment : Fragment() {
    private lateinit var binding: FragmentReviewWashBinding
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[WashViewModel::class.java]
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
                aerModel.editText?.setText(it?.AERModel, TextView.BufferType.EDITABLE)
                aerModel.editText?.setText(it?.AERModel)
                aerSerialNo.editText?.setText(it.AERSerial?.toString())
                detergentUsed.editText?.setText(it?.DetergentUsed)
                detergentLotNo.editText?.setText(it?.DetergentLotNo!!.toString())
                filterChangeDate.editText?.setText(SimpleDateFormat("dd/MM/yyyy").format(it.FilterChangeDate))
                disinfectantUsed.editText?.setText(it?.DisinfectantUsed)
                disinfectantLotNo.editText?.setText(it?.DisinfectantLotNo!!.toString())
                disinfectantChanged.editText?.setText(SimpleDateFormat("dd/MM/yyyy").format(it.DisinfectantChangedDate))
                scopeDryer.editText?.setText(it?.ScopeDryer!!.toString())
                dryerLevel.editText?.setText(it?.DryerLevel!!.toString())
                remarks.editText?.setText(it?.Remarks)
            }
        }
        viewModel.scopeData.observe(viewLifecycleOwner){
            binding.apply {
                brand.editText?.setText(it.scopeBrand,TextView.BufferType.EDITABLE)
                model.editText?.setText(it.scopeModel,TextView.BufferType.EDITABLE)
                serialNo.editText?.setText(it.scopeSerial.toString(),TextView.BufferType.EDITABLE)
            }
        }

        val button: Button = view.findViewById(R.id.button) as Button
        button.setOnClickListener{
            // validate the input
            Log.d("TestHash",viewModel.convertWashDataToMap().toString())
            // if true set it to true
            // replace with last fragment
            viewModel.insertIntoDB(viewModel.getScopeSerial())
            val intent = Intent (activity, MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }
}