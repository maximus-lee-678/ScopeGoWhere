package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentDetergentWashBinding
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.WashViewModel
import java.text.SimpleDateFormat
import java.util.*

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
        viewModel = ViewModelProvider(requireActivity())[WashViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = "Wash Equipment(3/5)"
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button) as Button
        val dateSelector = binding.filterChangeDate.editText
        dateSelector!!.setOnClickListener {
            val datePicker = viewModel.datePicker
                .setTitleText("Select Filter Changed Date")
                .build()
            datePicker.show(childFragmentManager,"Date Picker")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val date = dateFormat.format(Date((it)))
                binding.filterChangeDate.editText?.setText(date.toString())
                Log.d("DatePicker", date.toString())
            }
        }
        button.setOnClickListener{
            // validate the input
            var filterDate = binding.filterChangeDate.editText?.text.toString()
            val dateForFB = Utils.strToDate(filterDate)
            // if true set it to true
            viewModel.washData.postValue(viewModel.washData.value?.copy(
                DetergentUsed = binding.detergentUsed.editText?.text.toString(),
                DetergentLotNo = binding.detergentLotNo.editText?.text.toString().toInt(),
                FilterChangeDate = dateForFB
            ))
            // replace with last fragment
            val fragment = DisinfectantWashFragment()
            (activity as WashActivity).navbarNavigate(fragment)
        }
    }
}