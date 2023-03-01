package ict2105.team02.application.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ict2105.team02.application.R
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.databinding.FragmentScopeDetailBinding
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import java.text.SimpleDateFormat

private const val KEY_ENDOSCOPE_SERIAL = "SN"

class ScopeDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScopeDetailBinding

    private val viewModel by viewModels<ScopeDetailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScopeDetailBinding.inflate(inflater)

        binding.equipmentBannerLayout.visibility = View.GONE
        binding.scopeDetailLayout.visibility = View.INVISIBLE

        viewModel.scopeDetail.observe(this) {
            binding.equipmentNameTextView.text = it.model + it.serial
            binding.modelTextView.text = it.model
            binding.typeTextView.text = it.type
            binding.serialTextView.text = it.serial
            binding.statusTextView.text = it.status
            binding.nextSampleTextView.text = SimpleDateFormat("dd/MM/yyyy").format(it.nextSample)

            when(it.status) {
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
        val serial = arguments?.getString(KEY_ENDOSCOPE_SERIAL)
        if (serial != null) {
            viewModel.fetchScopeDetail(serial) {
                requireActivity().runOnUiThread {
                    binding.loadScopeProgressIndicator.visibility = View.GONE
                    binding.equipmentBannerLayout.visibility = View.VISIBLE
                    binding.scopeDetailLayout.visibility = View.VISIBLE
                }
            }
        }
        binding.washButton.setOnClickListener{
//            val fragment = WashEquipmentFragment()
//            (activity as MainActivity).navbarNavigate(fragment)
//            val fragmentManager = requireActivity().supportFragmentManager
//            val fragmentToRemove = this // replace R.id.fragment_container with your container id
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.remove(fragmentToRemove)
//            fragmentTransaction.commit()
            val intent = Intent (getActivity(), WashActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(endoscopeSerialNo: String) = ScopeDetailFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ENDOSCOPE_SERIAL, endoscopeSerialNo)
            }
        }
    }
}