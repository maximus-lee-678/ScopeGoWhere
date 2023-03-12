package ict2105.team02.application.ui.scopeStore

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import ict2105.team02.application.databinding.FragmentEditScopeBinding
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_BRAND
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_DATE
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_TYPE
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.ScopeUpdateViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class EditScopeFragment : Fragment() {
    private val viewModel by viewModels<ScopeUpdateViewModel>()

    private lateinit var binding: FragmentEditScopeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditScopeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scopeBrand.editText?.setText(arguments?.getString(KEY_ENDOSCOPE_BRAND), TextView.BufferType.EDITABLE)
        binding.scopeModel.editText?.setText(arguments?.getString(KEY_ENDOSCOPE_MODEL), TextView.BufferType.EDITABLE)
        binding.scopeSerial.editText?.setText(arguments?.getInt(KEY_ENDOSCOPE_SERIAL).toString(), TextView.BufferType.EDITABLE)
        binding.scopeType.editText?.setText(arguments?.getString(KEY_ENDOSCOPE_TYPE), TextView.BufferType.EDITABLE)
        binding.nextSampleDate.editText?.setText(arguments?.getString(KEY_ENDOSCOPE_DATE), TextView.BufferType.EDITABLE)

        binding.nextSampleDate.editText?.setOnClickListener{
            val currentDate = Calendar.getInstance()
            val builder =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
            val constraintsBuilder = CalendarConstraints.Builder()
            constraintsBuilder.setStart(currentDate.timeInMillis)
            constraintsBuilder.setEnd(currentDate.timeInMillis + TimeUnit.DAYS.toMillis(365))

            val validator = DateValidatorPointForward.from(currentDate.timeInMillis)
            constraintsBuilder.setValidator(validator)

            val constraints = constraintsBuilder.build()
            builder.setCalendarConstraints(constraints)
            val datePicker = builder.build()

            datePicker.show(childFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = it
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                binding.nextSampleDate.editText?.setText(formattedDate)
            }
        }

        binding.buttonUpdateScope.setOnClickListener{

            // update the details into the database
            if(TextUtils.isEmpty(binding.scopeBrand.editText?.text.toString())||
                TextUtils.isEmpty(binding.scopeModel.editText?.text.toString()) ||
                TextUtils.isEmpty(binding.scopeType.editText?.text.toString()) ||
                TextUtils.isEmpty(binding.nextSampleDate.editText?.text.toString())){
                    binding.errorMsg.text = "Please fill in all the fields"
            }else {
                val confirmationDialog = ConfirmationDialogFragment("Update endoscope?") {
                    // User clicked confirm
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateScope(
                            binding.scopeBrand.editText?.text.toString(),
                            binding.scopeModel.editText?.text.toString(),
                            binding.scopeSerial.editText?.text.toString().toInt(),
                            binding.scopeType.editText?.text.toString(),
                            Utils.strToDate(binding.nextSampleDate.editText?.text.toString()),
                            arguments?.getString(KEY_ENDOSCOPE_STATUS).toString()
                        )
                    }
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(requireContext(), "Scope Updated Successfully!", Toast.LENGTH_LONG).show()
                    requireActivity().finish()
                }
                confirmationDialog.show(parentFragmentManager, "ConfirmationDialog")
            }
        }

        binding.buttonDeleteScope.setOnClickListener {
            val confirmationDialog = ConfirmationDialogFragment("Are you sure you want to delete this endoscope?") {
                // User clicked confirm
                val serial = arguments?.getInt(KEY_ENDOSCOPE_SERIAL)
                if (serial != null) {
                    viewModel.deleteScope(serial)
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(requireContext(), "Scope Deleted Successfully!", Toast.LENGTH_LONG).show()
                    requireActivity().finish()
                }
            }
            confirmationDialog.show(parentFragmentManager, "ConfirmationDialog")
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(brand: String,serialNo: Int, modelNo: String, type: String, date: String, status: String) = EditScopeFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ENDOSCOPE_BRAND, brand)
                putString(KEY_ENDOSCOPE_MODEL, modelNo)
                putInt(KEY_ENDOSCOPE_SERIAL, serialNo)
                putString(KEY_ENDOSCOPE_TYPE, type)
                putString(KEY_ENDOSCOPE_DATE, date)
                putString(KEY_ENDOSCOPE_STATUS, status)
            }
        }
    }
}