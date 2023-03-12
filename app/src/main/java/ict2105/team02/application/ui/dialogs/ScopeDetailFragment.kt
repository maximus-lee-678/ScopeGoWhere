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
import ict2105.team02.application.ui.equipment.EquipLogFragment
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.ui.sample.ScanDialogFragment
import ict2105.team02.application.ui.scopeStore.EditScopeFragment
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import java.text.SimpleDateFormat

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

        val serial = arguments?.getInt(KEY_ENDOSCOPE_SERIAL)

        viewModel.scopeDetail.observe(this) {
            // Hide loading progress indicator
            binding.progressIndicatorLoadScopeDetail.visibility = View.GONE

            if (it == null) {
                // Show error
                Log.d("ScopeDetailFragment", "Failed to retrieve scope detail (null)")
                binding.frameLayoutScopeName.visibility = View.GONE
                binding.linearLayoutScopeDetail.visibility = View.GONE
                binding.textViewScopeDetailError.visibility = View.VISIBLE
                binding.textViewScopeDetailErrorSerial.text = "Serial: $serial"
                return@observe
            }

            binding.frameLayoutScopeName.visibility = View.VISIBLE
            binding.linearLayoutScopeDetail.visibility = View.VISIBLE
            binding.textViewScopeDetailError.visibility = View.GONE

            binding.textViewEquipmentName.text = it.scopeModel + it.scopeSerial
            binding.modelTextView.text = it.scopeModel
            binding.typeTextView.text = it.scopeType
            binding.serialTextView.text = it.scopeSerial.toString()
            binding.statusTextView.text = it.scopeStatus
            binding.nextSampleTextView.text = it.nextSampleDate.toString()
            binding.nextSampleTextView.text = SimpleDateFormat("dd/MM/yyyy").format(it.nextSampleDate)

            when(it.scopeStatus) {
                "Circulation" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_inventory_2_24)
                    binding.sampleButton.visibility = View.GONE
                }
                "Sampling" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_access_time_24)
                    binding.washButton.visibility = View.GONE
                }
                "Washing" -> {
                    binding.washButton.visibility = View.GONE
                }
            }
        }

        binding.editScopeButton.setOnClickListener{
            // may need to pass in parameter for the fragment to display the information
            val fragment = EditScopeFragment.
                            newInstance(viewModel.scopeDetail.value!!.scopeBrand,
                                viewModel.scopeDetail.value!!.scopeSerial,
                                viewModel.scopeDetail.value!!.scopeModel,
                                viewModel.scopeDetail.value!!.scopeType,
                                SimpleDateFormat("dd/MM/yyyy").format(viewModel.scopeDetail.value!!.nextSampleDate),
                                viewModel.scopeDetail.value!!.scopeStatus)
            (activity as MainActivity).navbarNavigate(fragment)
        }

        binding.washButton.setOnClickListener {
            val intent = Intent(activity, WashActivity::class.java)
            if (serial != null) {
                val scopeHashMap = HashMap<String, Any>()
                scopeHashMap["scopeSerial"] = viewModel.scopeDetail.value!!.scopeSerial
                scopeHashMap["scopeModel"] = viewModel.scopeDetail.value!!.scopeModel
                scopeHashMap["scopeBrand"] = viewModel.scopeDetail.value!!.scopeBrand
                intent.putExtra("scopeDetails", scopeHashMap)
            }
            activity?.startActivity(intent)
            activity?.finish()
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

            val fragment = EquipLogFragment.newInstance(scopeSerial, scopeModel, scopeStatus)
            (activity as MainActivity).navbarNavigate(fragment)
        }

        if (serial != null) {
            viewModel.fetchScopeDetail(serial)
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