package ict2105.team02.application.equipment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R

class EquipmentAdapter (
    private val context: Context,
    private val equipments: List<Equipment>
) : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    inner class EquipmentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.rv_equipment_list_item_text)
        val statusTextView: TextView = view.findViewById(R.id.rv_equipment_list_item_secondary_text)

        init {
            view.setOnClickListener {
                // Probably redirect to view endoscope detail activity
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_equipment_item, parent, false)
        return EquipmentViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.nameTextView.text = equipments[position].name
        holder.statusTextView.text = "In circulation"
    }

    override fun getItemCount(): Int {
        return equipments.size
    }

}