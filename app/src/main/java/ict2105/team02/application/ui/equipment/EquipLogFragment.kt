package ict2105.team02.application.ui.equipment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.databinding.FragmentEquipLogBinding
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.recyclerview.EquipLogAdapter
import ict2105.team02.application.ui.dialogs.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.ui.dialogs.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.ui.dialogs.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.ui.dialogs.ScopeDetailFragment
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EquipLogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EquipLogFragment : Fragment() {
    private lateinit var binding: FragmentEquipLogBinding
    private lateinit var equip: Endoscope
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EquipLogAdapter
    private val scopeDetailViewModel by viewModels<ScopeDetailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEquipLogBinding.inflate(inflater)
//        recyclerView = binding.equipHistoryList
//        adapter = EquipLogAdapter()
//        recyclerView.adapter = adapter

        binding.modelSerialTextView.text = arguments?.getString(KEY_ENDOSCOPE_MODEL)+arguments?.getString(KEY_ENDOSCOPE_SERIAL)
        binding.statusTextView.text = arguments?.getString(KEY_ENDOSCOPE_STATUS)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        scopeDetailViewModel.fetchLogDetail()

    }

    companion object {
        @JvmStatic
        fun newInstance(serialNo: String, modelNo: String, status: String) = EquipLogFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ENDOSCOPE_SERIAL, serialNo)
                putString(KEY_ENDOSCOPE_MODEL, modelNo)
                putString(KEY_ENDOSCOPE_STATUS, status)
            }
        }
    }
}