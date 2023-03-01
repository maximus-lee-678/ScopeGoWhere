package ict2105.team02.application.ui.dialogs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentEquipLogBinding
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
import ict2105.team02.application.recyclerview.EquipLogAdapter
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.viewmodel.EquipLogViewModel
import ict2105.team02.application.viewmodel.LoginViewModel
import ict2105.team02.application.viewmodel.ScopeDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EquipLogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EquipLogFragment : Fragment() {
    private lateinit var binding: FragmentEquipLogBinding
    private lateinit var equipViewModel: EquipLogViewModel
    private lateinit var scopeDetailViewModel: ScopeDetailViewModel
    private lateinit var equip: Endoscope
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EquipLogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEquipLogBinding.inflate(inflater)

        //connect to view model
        equipViewModel = ViewModelProvider(requireActivity())[EquipLogViewModel::class.java]
        scopeDetailViewModel = ViewModelProvider(requireActivity())[ScopeDetailViewModel::class.java]

        scopeDetailViewModel.scopeDetail.observe(requireActivity()) { endoscope ->
            equip = endoscope
        }

        recyclerView = binding.equipHistoryList
        adapter = EquipLogAdapter()
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        equipViewModel.getScopeLog(equip)

        equipViewModel.equipDetail.observe(requireActivity(), Observer {
            when(it){
                is UiState.Loading -> {
                    // nothing to do
                }
                is UiState.Success -> {
                    binding.modelSerialTextView.text = it.data?.model.toString() + it.data?.serial.toString()
                    binding.statusTextView.text = it.data?.status.toString()

                    val date = it.data?.nextSample
                    val formatter = SimpleDateFormat("dd-MM-yyyy")
                    val dateOnly = formatter.format(date)
                    binding.nextSampleTextView.text = dateOnly.toString()
                    adapter.submitList(it.data?.history)

                    // DEBUG
//                    Log.d("TAG", it.data?.model.toString())
//                    Log.d("TAG", it.data?.serial.toString())
//                    Log.d("TAG", it.data?.type.toString())
//                    Log.d("TAG", it.data?.status.toString())
//                    Log.d("TAG", it.data?.nextSample.toString())
//                    Log.d("TAG", it.data?.history?.get(0)?.date.toString())
//                    Log.d("TAG", it.data?.history?.get(0)?.doneBy.toString())
//                    Log.d("TAG", it.data?.history?.get(0)?.transaction.toString())
                }
                is UiState.Error -> {
                    // nothing to do
                    binding.modelSerialTextView.text = it.message
                    binding.statusTextView.visibility = View.INVISIBLE
                    binding.logTitleTextView.visibility = View.INVISIBLE
                    binding.equipHistoryList.visibility = View.INVISIBLE
                    binding.nextSampleTextView.visibility = View.INVISIBLE
                }
            }

        })
    }
}