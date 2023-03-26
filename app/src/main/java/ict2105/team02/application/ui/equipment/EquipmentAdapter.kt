package ict2105.team02.application.ui.equipment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.Endoscope

// Adapter for displaying a list of endoscopes in a RecyclerView
class EquipmentAdapter : ListAdapter<Endoscope, EquipmentAdapter.EquipmentViewHolder>
    (EndoscopeComparator()) {
    // Callback for when an item is clicked
    var onItemClick: ((Endoscope) -> Unit)? = null

    // ViewHolder for a single endoscope item
    inner class EquipmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Views in the item layout
        val nameTextView: TextView = view.findViewById(R.id.equipmentRVItemText)
        val statusTextView: TextView = view.findViewById(R.id.equipmentRVItemSecondaryText)

        init {
            // Set a click listener for the item view
            view.setOnClickListener {
                // Invoke the onItemClick callback with the corresponding Endoscope object
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    // DiffUtil callback for comparing old and new Endoscope objects
    class EndoscopeComparator : DiffUtil.ItemCallback<Endoscope>() {
        // Check if two Endoscope objects have the same ID (scopeSerial)
        override fun areItemsTheSame(oldItem: Endoscope, newItem: Endoscope): Boolean {
            return oldItem == newItem
        }

        // Check if the contents of two Endoscope objects are the same (just compares scopeSerial here)
        override fun areContentsTheSame(oldItem: Endoscope, newItem: Endoscope): Boolean {
            return oldItem.scopeSerial == newItem.scopeSerial
        }
    }

    // Create a new ViewHolder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        // Inflate the item layout and create a new ViewHolder for it
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_equipment_item, parent, false)
        return EquipmentViewHolder(view)
    }

    // Bind data to the views in a ViewHolder
    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        // Get the Endoscope object for the current item
        val item = getItem(position)
        // Set the name of the endoscope to a combination of its model and serial number
        holder.nameTextView.text = item.scopeModel + item.scopeSerial
        // Set the status of the endoscope
        holder.statusTextView.text = item.scopeStatus
    }
}
