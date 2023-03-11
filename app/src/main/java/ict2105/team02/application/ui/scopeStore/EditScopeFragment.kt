package ict2105.team02.application.ui.scopeStore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentEditScopeBinding
import ict2105.team02.application.databinding.FragmentHomeBinding
import ict2105.team02.application.ui.equipment.EquipLogFragment
import ict2105.team02.application.ui.main.HomeFragment
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import ict2105.team02.application.viewmodel.ScopeUpdateViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

const val KEY_ENDOSCOPE_BRAND = "BRAND"
const val KEY_ENDOSCOPE_SERIAL = "SN"
const val KEY_ENDOSCOPE_MODEL = "MODEL"
const val KEY_ENDOSCOPE_TYPE = "TYPE"
const val KEY_ENDOSCOPE_DATE = "DATE"

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

        binding.buttonUpdateScope.setOnClickListener{
            // update the details into the database
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.updateScope(
                    binding.scopeBrand.editText?.text.toString(),
                    binding.scopeModel.editText?.text.toString(),
                    binding.scopeSerial.editText?.text.toString().toInt(),
                    binding.scopeType.editText?.text.toString(),
                    Utils.strToDate(binding.nextSampleDate.editText?.text.toString())
                )
            }
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(brand: String,serialNo: Int, modelNo: String, type: String, date: String) = EditScopeFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ENDOSCOPE_BRAND, brand)
                putString(KEY_ENDOSCOPE_MODEL, modelNo)
                putInt(KEY_ENDOSCOPE_SERIAL, serialNo)
                putString(KEY_ENDOSCOPE_TYPE, type)
                putString(KEY_ENDOSCOPE_DATE, date)
            }
        }
    }
}