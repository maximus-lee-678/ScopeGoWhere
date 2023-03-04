package ict2105.team02.application.ui.equipment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.databinding.FragmentEquipLogBinding
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.recyclerview.EquipLogAdapter
import ict2105.team02.application.ui.dialogs.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.ui.dialogs.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.ui.dialogs.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.viewmodel.ScopeDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [EquipLogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EquipLogFragment : Fragment() {
    private lateinit var binding: FragmentEquipLogBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EquipLogAdapter
    private val scopeDetailViewModel by viewModels<ScopeDetailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEquipLogBinding.inflate(inflater)
        recyclerView = binding.equipHistoryList
        adapter = EquipLogAdapter()
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = arguments?.getString(KEY_ENDOSCOPE_MODEL).toString()
        val serial = arguments?.getInt(KEY_ENDOSCOPE_SERIAL)
        val status = arguments?.getString(KEY_ENDOSCOPE_STATUS).toString()

        binding.modelSerialTextView.text = model + serial.toString()
        binding.statusTextView.text = status

        if (serial != null) {
            scopeDetailViewModel.fetchLogDetail(serial)
        }

        scopeDetailViewModel.scopeLogDetail.observe(requireActivity(), Observer {
            when(it){
                is UiState.Loading -> {
                    // nothing to do
                }
                is UiState.Success -> {
                    // uiState Text View
                    binding.uiStateTextView.visibility = View.GONE
                    binding.equipHistoryList.visibility = View.VISIBLE
                    adapter.submitList(it.data)

                }
                is UiState.Error -> {
                    // uiState Text View
                    binding.uiStateTextView.text = it.message
                    binding.uiStateTextView.visibility = View.VISIBLE
                    binding.equipHistoryList.visibility = View.INVISIBLE

                }
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(serialNo: Int, modelNo: String, status: String) = EquipLogFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_ENDOSCOPE_SERIAL, serialNo)
                putString(KEY_ENDOSCOPE_MODEL, modelNo)
                putString(KEY_ENDOSCOPE_STATUS, status)
            }
        }
    }
}