package ict2105.team02.application.ui.sample

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentRepeatOfMsSampleBinding
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.ui.wash.WasherWashFragment
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.SampleViewModel
import java.text.SimpleDateFormat
import java.util.*

class RepeatOfMsSampleFragment : Fragment() {
    private lateinit var binding : FragmentRepeatOfMsSampleBinding
    private lateinit var viewModel : SampleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepeatOfMsSampleBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(SampleViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.setTitle("Sample Equipment(3/4)")
        binding.repeatDate.editText?.setOnClickListener{
            val datePicker = viewModel.datePicker
                .setTitleText("Select Date of Result")
                .build()
            datePicker.show(childFragmentManager,"Date Picker")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val date = dateFormat.format(Date((it)))
                binding.repeatDate.editText?.setText(date.toString())
                Log.d("DatePicker", date.toString())
            }
        }
        binding.button.setOnClickListener{
            if(TextUtils.isEmpty(binding.quarantineRequiredDropDown.editText?.text) ||
                TextUtils.isEmpty(binding.repeatDate.editText?.text)||
                TextUtils.isEmpty(binding.borescopeDropdown.editText?.text)){
                binding.errorMsgRepeat.text = "Please fill in all the fields"
            }
            else{
                // validate the input
                // if true set it to true
                viewModel.sampleData.postValue(viewModel.sampleData.value?.copy(
                    quarantinePeriod = binding.quarantineRequiredDropDown.editText?.text.toString(),
                    repeatDateMS = Utils.strToDate(binding.repeatDate.editText?.text.toString()),
                    borescope = binding.borescopeDropdown.editText?.text.toString()))
                // replace with last fragment
                val fragment = AtpSampleFragment()
                (activity as SampleActivity).navbarNavigate(fragment)
            }
        }
    }

}