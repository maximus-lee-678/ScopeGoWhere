package ict2105.team02.application.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentEquipmentBinding

class EquipmentFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentBinding

    private val equipments = listOf("Cilantro", "Beans", "Cheese", "Oil", "Tomato", "Salt", "Pepper", "Flour",
        "Corn", "Garlic", "Lime", "Onion", "Rice", "Cabbage", "Avocado").map { Equipment(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEquipmentBinding.inflate(inflater)

        // Setup recyclerview
        binding.recyclerviewEquipments.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EquipmentAdapter(requireContext(), equipments)
        }

        return binding.root
    }
}