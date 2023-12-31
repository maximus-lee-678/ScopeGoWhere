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
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.equipment.EditScopeActivity
import ict2105.team02.application.ui.equipment.EquipmentLogActivity
import ict2105.team02.application.ui.sample.SampleActivity
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.utils.Constants
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_BRAND
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_NEXT_SAMPLE_DATE
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_TYPE
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.ScopeDetailViewModel

class ScopeDetailDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScopeDetailBinding

    private val scopeDetailViewModel: ScopeDetailViewModel by viewModels {
        ViewModelFactory(
            "ScopeDetailViewModel",
            activity?.application as MainApplication
        )
    }

    private var argSerial: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScopeDetailBinding.inflate(inflater)

        setUILoading()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argSerial = arguments?.getInt(KEY_ENDOSCOPE_SERIAL) ?: throw NullPointerException()

        scopeDetailViewModel.scopeDetail.observe(this) {
            if (it == null) {
                setUILoadFailed(argSerial)
                return@observe
            }

            setUILoadSuccess(it)
        }

        binding.editScopeButton.setOnClickListener{
            val endoscope = scopeDetailViewModel.scopeDetail.value
            if (endoscope == null) {
                Log.d(TAG, "[Edit Scope Button] ViewModel endoscope data is null!")
                return@setOnClickListener
            }

            startActivity(Intent(requireContext(), EditScopeActivity::class.java).apply {
                putExtra(KEY_ENDOSCOPE_SERIAL, endoscope.scopeSerial)
                putExtra(KEY_ENDOSCOPE_BRAND, endoscope.scopeBrand)
                putExtra(KEY_ENDOSCOPE_MODEL, endoscope.scopeModel)
                putExtra(KEY_ENDOSCOPE_TYPE, endoscope.scopeType)
                putExtra(KEY_ENDOSCOPE_NEXT_SAMPLE_DATE, endoscope.nextSampleDate.toDateString())
                putExtra(KEY_ENDOSCOPE_STATUS, endoscope.scopeStatus)
            })
        }

        binding.washButton.setOnClickListener {
            startActivity(Intent(requireContext(), WashActivity::class.java).apply {
                putExtra(KEY_ENDOSCOPE_SERIAL, scopeDetailViewModel.scopeDetail.value!!.scopeSerial)
                putExtra(KEY_ENDOSCOPE_MODEL, scopeDetailViewModel.scopeDetail.value!!.scopeModel)
                putExtra(KEY_ENDOSCOPE_BRAND, scopeDetailViewModel.scopeDetail.value!!.scopeBrand)
            })
            dismiss()
        }

        binding.sampleButton.setOnClickListener {
            startActivity(Intent(requireContext(), SampleActivity::class.java).apply {
                putExtra(KEY_ENDOSCOPE_SERIAL, scopeDetailViewModel.scopeDetail.value!!.scopeSerial)
                putExtra(KEY_ENDOSCOPE_MODEL, scopeDetailViewModel.scopeDetail.value!!.scopeModel)
                putExtra(KEY_ENDOSCOPE_BRAND, scopeDetailViewModel.scopeDetail.value!!.scopeBrand)
            })
            dismiss()
        }

        binding.circulationButton.setOnClickListener {
            ConfirmationDialogFragment(resources.getString(R.string.dialog_return_to_circulation)) {
                // User clicked confirm
                val serial = scopeDetailViewModel.scopeDetail.value!!.scopeSerial
                scopeDetailViewModel.returnScopeToCirculation(serial)
                scopeDetailViewModel.fetchScopeDetail(serial)
            }.show(childFragmentManager, null)
        }

        binding.viewLogsButton.setOnClickListener{
            // replace with last fragment
            val scopeSerial = scopeDetailViewModel.scopeDetail.value!!.scopeSerial
            val scopeModel = scopeDetailViewModel.scopeDetail.value!!.scopeModel
            val scopeStatus = scopeDetailViewModel.scopeDetail.value!!.scopeStatus

            val intent = Intent(requireContext(), EquipmentLogActivity::class.java)
            intent.putExtra(KEY_ENDOSCOPE_SERIAL, scopeSerial)
            intent.putExtra(KEY_ENDOSCOPE_MODEL, scopeModel)
            intent.putExtra(KEY_ENDOSCOPE_STATUS, scopeStatus)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        scopeDetailViewModel.fetchScopeDetail(argSerial)
    }

    private fun setUILoading() {
        binding.apply {
            frameLayoutScopeName.visibility = View.GONE
            linearLayoutScopeDetail.visibility = View.INVISIBLE
            constrainLayoutScopeStatusSample.visibility = View.INVISIBLE
            progressIndicatorLoadScopeDetail.visibility = View.VISIBLE
            textViewScopeDetailError.visibility = View.GONE
        }
    }

    private fun setUILoadSuccess(e: Endoscope) {
        binding.apply {
            // Hide loading progress indicator and error
            progressIndicatorLoadScopeDetail.visibility = View.GONE
            textViewScopeDetailError.visibility = View.GONE

            // Show UI for endoscope data visible
            frameLayoutScopeName.visibility = View.VISIBLE
            linearLayoutScopeDetail.visibility = View.VISIBLE
            constrainLayoutScopeStatusSample.visibility = View.VISIBLE

            // Update UI with endoscope data
            textViewEquipmentName.text = getString(R.string.endoscope_name, e.scopeModel, e.scopeSerial.toString())
            modelTextView.text = e.scopeModel
            typeTextView.text = e.scopeType
            serialTextView.text = e.scopeSerial.toString()
            statusTextView.text = e.scopeStatus
            nextSampleTextView.text = e.nextSampleDate.toString()
            nextSampleTextView.text = e.nextSampleDate.toDateString()

            // Show or hide buttons depending on scope status
            when(e.scopeStatus) {
                Constants.ENDOSCOPE_CIRCULATION -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_inventory_2_24)
                    binding.sampleButton.visibility = View.GONE
                    binding.washButton.visibility = View.VISIBLE
                    binding.circulationButton.visibility = View.GONE
                }
                Constants.ENDOSCOPE_WASH -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_access_time_24)
                    binding.sampleButton.visibility = View.VISIBLE
                    binding.washButton.visibility = View.GONE
                    binding.circulationButton.visibility = View.GONE
                }
                Constants.ENDOSCOPE_SAMPLE -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_access_time_24)
                    binding.sampleButton.visibility = View.GONE
                    binding.washButton.visibility = View.GONE
                    binding.circulationButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUILoadFailed(serial: Int) {
        // Show error
        Log.d("ScopeDetailFragment", "Failed to retrieve scope detail (null)")
        binding.apply {
            frameLayoutScopeName.visibility = View.GONE
            linearLayoutScopeDetail.visibility = View.GONE
            textViewScopeDetailError.visibility = View.VISIBLE
            textViewScopeDetailErrorSerial.text = getString(R.string.endoscope_serial, serial.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(serialNo: Int) = ScopeDetailDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_ENDOSCOPE_SERIAL, serialNo)
            }
        }
    }

}