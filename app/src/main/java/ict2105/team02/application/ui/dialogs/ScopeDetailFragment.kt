package ict2105.team02.application.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentScopeDetailBinding
import ict2105.team02.application.ui.equipment.EditScopeActivity
import ict2105.team02.application.ui.sample.ScanDialogFragment
import ict2105.team02.application.ui.equipment.EquipmentLogActivity
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_BRAND
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_NEXT_SAMPLE_DATE
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_TYPE
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.ScopeDetailViewModel

class ScopeDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScopeDetailBinding

    private val viewModel by viewModels<ScopeDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScopeDetailBinding.inflate(inflater)

        binding.frameLayoutScopeName.visibility = View.GONE
        binding.linearLayoutScopeDetail.visibility = View.INVISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argSerial = arguments?.getInt(KEY_ENDOSCOPE_SERIAL)

        viewModel.scopeDetail.observe(this) {
            // Hide loading progress indicator
            binding.progressIndicatorLoadScopeDetail.visibility = View.GONE

            if (it == null) {
                // Show error
                Log.d("ScopeDetailFragment", "Failed to retrieve scope detail (null)")
                binding.frameLayoutScopeName.visibility = View.GONE
                binding.linearLayoutScopeDetail.visibility = View.GONE
                binding.textViewScopeDetailError.visibility = View.VISIBLE
                binding.textViewScopeDetailErrorSerial.text = getString(R.string.endoscope_serial, argSerial.toString())
                return@observe
            }

            binding.frameLayoutScopeName.visibility = View.VISIBLE
            binding.linearLayoutScopeDetail.visibility = View.VISIBLE
            binding.textViewScopeDetailError.visibility = View.GONE

            binding.textViewEquipmentName.text = getString(R.string.endoscope_name, it.scopeModel, it.scopeSerial.toString())
            binding.modelTextView.text = it.scopeModel
            binding.typeTextView.text = it.scopeType
            binding.serialTextView.text = it.scopeSerial.toString()
            binding.statusTextView.text = it.scopeStatus
            binding.nextSampleTextView.text = it.nextSampleDate.toString()
            binding.nextSampleTextView.text = it.nextSampleDate.toDateString()

            when(it.scopeStatus) {
                "Circulation" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_inventory_2_24)
                    binding.sampleButton.visibility = View.GONE
                }
                "Washing" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_access_time_24)
                    binding.washButton.visibility = View.GONE
                }
                "Sampling" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_access_time_24)
                    binding.washButton.visibility = View.GONE
                }
            }
        }

        binding.editScopeButton.setOnClickListener{
            // replace with last fragment
            val serial = viewModel.scopeDetail.value!!.scopeSerial
            val brand = viewModel.scopeDetail.value!!.scopeBrand
            val model = viewModel.scopeDetail.value!!.scopeModel
            val type = viewModel.scopeDetail.value!!.scopeType
            val nextSampleDate = viewModel.scopeDetail.value!!.nextSampleDate
            val status = viewModel.scopeDetail.value!!.scopeStatus

            val intent = Intent(requireContext(), EditScopeActivity::class.java)
            intent.putExtra(KEY_ENDOSCOPE_SERIAL, serial)
            intent.putExtra(KEY_ENDOSCOPE_BRAND, brand)
            intent.putExtra(KEY_ENDOSCOPE_MODEL, model)
            intent.putExtra(KEY_ENDOSCOPE_TYPE, type)
            intent.putExtra(KEY_ENDOSCOPE_NEXT_SAMPLE_DATE, nextSampleDate.toDateString())
            intent.putExtra(KEY_ENDOSCOPE_STATUS, status)
            startActivity(intent)
        }

        binding.washButton.setOnClickListener {
            val intent = Intent(activity, WashActivity::class.java)
            if (argSerial != null) {
                val scopeHashMap = HashMap<String, Any>()
                scopeHashMap["scopeSerial"] = viewModel.scopeDetail.value!!.scopeSerial
                scopeHashMap["scopeModel"] = viewModel.scopeDetail.value!!.scopeModel
                scopeHashMap["scopeBrand"] = viewModel.scopeDetail.value!!.scopeBrand
                intent.putExtra("scopeDetails", scopeHashMap)
                startActivity(intent)
            }
        }

        binding.sampleButton.setOnClickListener {
//            val intent = Intent(getActivity(), SampleActivity::class.java)
//            getActivity()?.startActivity(intent)
            ScanDialogFragment().show(childFragmentManager,"")
        }

        binding.viewLogsButton.setOnClickListener{
            // replace with last fragment
            val scopeSerial = viewModel.scopeDetail.value!!.scopeSerial
            val scopeModel = viewModel.scopeDetail.value!!.scopeModel
            val scopeStatus = viewModel.scopeDetail.value!!.scopeStatus

            val intent = Intent(requireContext(), EquipmentLogActivity::class.java)
            intent.putExtra(KEY_ENDOSCOPE_SERIAL, scopeSerial)
            intent.putExtra(KEY_ENDOSCOPE_MODEL, scopeModel)
            intent.putExtra(KEY_ENDOSCOPE_STATUS, scopeStatus)
            startActivity(intent)

            this.dismiss()
        }

        if (argSerial != null) {
            viewModel.fetchScopeDetail(argSerial)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(serialNo: Int) = ScopeDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_ENDOSCOPE_SERIAL, serialNo)
            }
        }
    }

}