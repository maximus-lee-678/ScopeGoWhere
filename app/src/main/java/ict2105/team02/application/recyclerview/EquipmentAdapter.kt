package ict2105.team02.application.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.Endoscope

class EquipmentAdapter ()
    : ListAdapter<Endoscope, EquipmentAdapter.EquipmentViewHolder>(EndoscopeComparator()) {

    inner class EquipmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.equipmentRVItemText)
        val statusTextView: TextView = view.findViewById(R.id.equipmentRVItemSecondaryText)

        init {
            view.setOnClickListener {
                // Probably redirect to view endoscope detail activity
            }
        }
    }

    class EndoscopeComparator : DiffUtil.ItemCallback<Endoscope>() {
        override fun areItemsTheSame(oldItem: Endoscope, newItem: Endoscope): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Endoscope, newItem: Endoscope): Boolean {
            return oldItem.serial == newItem.serial
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_equipment_item, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        val item = getItem(position)
        holder.nameTextView.text = item.serial
        holder.statusTextView.text = item.status
    }
}