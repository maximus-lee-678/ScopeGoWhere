package ict2105.team02.application.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ict2105.team02.application.R
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.databinding.FragmentScopeDetailBinding
import ict2105.team02.application.ui.sample.SampleActivity
import ict2105.team02.application.ui.equipment.EquipLogFragment
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import java.text.SimpleDateFormat

const val KEY_ENDOSCOPE_SERIAL = "SN"
const val KEY_ENDOSCOPE_MODEL = "MODEL"
const val KEY_ENDOSCOPE_STATUS = "STATUS"

class ScopeDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScopeDetailBinding

    private val viewModel by viewModels<ScopeDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScopeDetailBinding.inflate(inflater)

        binding.equipmentBannerLayout.visibility = View.GONE
        binding.scopeDetailLayout.visibility = View.INVISIBLE

        viewModel.scopeDetail.observe(this) {
            binding.equipmentNameTextView.text = it.ScopeModel + it.ScopeSerial
            binding.modelTextView.text = it.ScopeModel
            binding.typeTextView.text = it.ScopeType
            binding.serialTextView.text = it.ScopeSerial.toString()
            binding.statusTextView.text = it.ScopeStatus
            binding.nextSampleTextView.text = SimpleDateFormat("dd/MM/yyyy").format(it.NextSampleDate)

            when(it.ScopeStatus) {
                "In storage" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_inventory_2_24)
                }
                "Out for sampling" -> {
                    binding.statusIconImageView.setImageResource(R.drawable.outline_access_time_24)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serial = arguments?.getInt(KEY_ENDOSCOPE_SERIAL)
        if (serial != null) {
            viewModel.fetchScopeDetail(serial) {
                requireActivity().runOnUiThread {
                    binding.loadScopeProgressIndicator.visibility = View.GONE
                    binding.equipmentBannerLayout.visibility = View.VISIBLE
                    binding.scopeDetailLayout.visibility = View.VISIBLE
                }
            }
        }
        binding.washButton.setOnClickListener {
//            val fragment = WashEquipmentFragment()
//            (activity as MainActivity).navbarNavigate(fragment)
//            val fragmentManager = requireActivity().supportFragmentManager
//            val fragmentToRemove = this // replace R.id.fragment_container with your container id
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.remove(fragmentToRemove)
//            fragmentTransaction.commit()
            val intent = Intent(getActivity(), WashActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        binding.sampleButton.setOnClickListener {
            val intent = Intent(getActivity(), SampleActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        binding.viewLogsButton.setOnClickListener{
            // replace with last fragment
            var serial = viewModel.scopeDetail.value!!.ScopeSerial
            var model = viewModel.scopeDetail.value!!.ScopeModel
            var status = viewModel.scopeDetail.value!!.ScopeStatus

            val fragment = EquipLogFragment.newInstance(serial,model,status)
            (activity as MainActivity).navbarNavigate(fragment)
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