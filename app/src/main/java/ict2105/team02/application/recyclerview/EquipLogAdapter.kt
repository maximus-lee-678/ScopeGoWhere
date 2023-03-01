package ict2105.team02.application.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.EndoscopeTransaction
import java.util.*

class EquipLogAdapter:
    ListAdapter<EndoscopeTransaction, EquipLogAdapter.EndoscopeTransactionViewHolder>(EndoscopeTransactionComparator())
{

    class EndoscopeTransactionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val doneByTextView: TextView = itemView.findViewById(R.id.doneByTextView)
        private val transactionTextView: TextView = itemView.findViewById(R.id.transactionTextView)

        fun bind(date: Date, doneBy: String, transaction: String){
            dateTextView.text =  date.toString()
            doneByTextView.text = doneBy.toString()
            transactionTextView.text = transaction.toString()
        }

        companion object{
            fun create(parent: ViewGroup): EndoscopeTransactionViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_log_view_item, parent, false)
                return EndoscopeTransactionViewHolder(view)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EndoscopeTransactionViewHolder {
        return EndoscopeTransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EndoscopeTransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.date, current.doneBy, current.transaction)
    }

    class EndoscopeTransactionComparator: DiffUtil.ItemCallback<EndoscopeTransaction>() {
        override fun areItemsTheSame(
            oldItem: EndoscopeTransaction,
            newItem: EndoscopeTransaction
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EndoscopeTransaction,
            newItem: EndoscopeTransaction
        ): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }

    }
}
