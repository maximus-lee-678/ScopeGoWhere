package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.databinding.FragmentFluidResultSampleBinding
import ict2105.team02.application.ui.wash.DisinfectantWashFragment
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.viewmodel.SampleViewModel
import java.text.SimpleDateFormat
import java.util.*

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

        binding.dateOfResultInput.editText?.setOnClickListener{
            val datePicker = viewModel.datePicker
                .setTitleText("Select Date of Result")
                .build()
            datePicker.show(childFragmentManager,"Date Picker")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val date = dateFormat.format(Date((it)))
                binding.dateOfResultInput.editText?.setText(date.toString())
                Log.d("DatePicker", date.toString())
            }
        }
//        val button: Button = view.findViewById(R.id.button) as Button
        binding.button.setOnClickListener{
            if(TextUtils.isEmpty(binding.dateOfResultInput.editText?.text) ||
                TextUtils.isEmpty(binding.fluidResultInput.editText?.text)||
                TextUtils.isEmpty(binding.actionInput.editText?.text)){
                binding.errorMsgFluid.text = "Please fill in all the fields"
            }
            else{
                // validate the input
                // if true update the viewModel
                viewModel.sampleData.postValue(viewModel.sampleData.value?.copy(
                    dateOfFluidResult = binding.dateOfResultInput.editText?.text.toString().parseDateString()!!,
                    fluidResult = binding.fluidResultInput.editText?.text.toString(),
                    actionFluid = binding.actionInput.editText?.text.toString(),
                    cultureCommentFluid = binding.cultureCommentInput.editText?.text.toString()))

                val fragment = SwabResultSampleFragment()
                (activity as SampleActivity).navbarNavigate(fragment)
            }
        }
    }
}