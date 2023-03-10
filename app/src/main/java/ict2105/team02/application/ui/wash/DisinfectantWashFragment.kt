package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentDisinfectantWashBinding
import ict2105.team02.application.viewmodel.WashViewModel
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [DisinfectantWashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisinfectantWashFragment : Fragment() {
    private lateinit var binding: FragmentDisinfectantWashBinding
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: WashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDisinfectantWashBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[WashViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = "Wash Equipment(4/5)"
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        val disinfectantChange = binding.disinfectantChanged.editText
        disinfectantChange?.setOnClickListener {
            val datePicker = viewModel.datePicker
                .setTitleText("Date of Disinfectant Change")
                .build()
            datePicker.show(childFragmentManager, "Disinfectant")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val date = dateFormat.format(Date((it)))
                binding.disinfectantChanged.editText?.setText(date.toString())
                Log.d("DatePicker", date.toString())
            }
        }
        button.setOnClickListener{
            // validate the input
            var filterDate = binding.disinfectantChanged.editText?.text.toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val dateForFB = sdf.parse(filterDate)
            // if true set it to true
            viewModel.washData.postValue(viewModel.washData.value?.copy(
                DisinfectantUsed = binding.disinfectantUsed.editText?.text.toString(),
                DisinfectantLotNo = binding.disinfectantLotNo.editText?.text.toString().toInt(),
                DisinfectantChangedDate = dateForFB
            ))
            // replace with last fragment
            val fragment = DryingCabinetWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}