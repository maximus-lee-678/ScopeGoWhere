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
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentSwabResultSampleBinding
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.ui.wash.WasherWashFragment
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.SampleViewModel
import java.text.SimpleDateFormat
import java.util.*

class SwabResultSampleFragment : Fragment() {
    private lateinit var binding: FragmentSwabResultSampleBinding
    private lateinit var viewModel: SampleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSwabResultSampleBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(SampleViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.setTitle("Update Result(2/4)")
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
        binding.button.setOnClickListener{
            if(TextUtils.isEmpty(binding.dateOfResultInput.editText?.text) ||
                TextUtils.isEmpty(binding.swabResultInput.editText?.text)||
                TextUtils.isEmpty(binding.actionInput.editText?.text)){
                binding.errorMsgSwab.text = "Please fill in all the fields"
            }
            else{
                // validate the input
                // if true set it to true
                viewModel.sampleData.postValue(viewModel.sampleData.value?.copy(
                    dateOfSwabResult = Utils.strToDate(binding.dateOfResultInput.editText?.text.toString()),
                    swabResult = binding.swabResultInput.editText?.text.toString(),
                    actionSwab = binding.actionInput.editText?.text.toString(),
                    cultureCommentSwab = binding.cultureCommentInput.editText?.text.toString()))
                // replace with last fragment
                val fragment = RepeatOfMsSampleFragment()
                (activity as SampleActivity).navbarNavigate(fragment)
            }
        }
    }
}