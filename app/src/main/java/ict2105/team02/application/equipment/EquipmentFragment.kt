package ict2105.team02.application.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentEquipmentBinding
import ict2105.team02.application.model.Endoscope
import java.util.*

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding

    private val endoscopeSerial = (1..15).map { String.format("%03d", it) }
    private val endoscopeName = listOf("AAA", "AAB", "AAC", "ABC", "ABD", "ABE")

    private val equipments = endoscopeSerial.mapIndexed { index, serial ->
        Endoscope(serial, endoscopeName[index % endoscopeName.size], "A", "In circulation", Date().toString(), listOf())}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEquipmentBinding.inflate(inflater)

        // Setup recyclerview
        binding.equipmentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EquipmentAdapter(requireContext(), equipments)
        }

        return binding.root
    }
}