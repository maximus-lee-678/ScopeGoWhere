package ict2105.team02.application.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.schedule.DateDetails

class CalendarWeekAdapter(
    private var dateDetails: DateDetails
) : RecyclerView.Adapter<CalendarWeekAdapter.ItemViewHolder>() {
    var selectedPos: Int = dateDetails.day!! - dateDetails.weekArray[0][0]
    var selectedWeekOffset: Int = 0

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewCalendarWeekItem)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarWeekAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_calendar_week_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CalendarWeekAdapter.ItemViewHolder, position: Int) {
        holder.textView.text = String.format(
            "[%d] %d-%d-%d",
            position,
            dateDetails.weekArray[position][0],
            dateDetails.weekArray[position][1],
            dateDetails.weekArray[position][2]
        )

        if (selectedPos == position && selectedWeekOffset == 0) {
            holder.itemView.setBackgroundColor(0x7F00FF00.toInt())
        } else {
            holder.itemView.setBackgroundColor(0x00FFFFFF.toInt())
        }

        holder.textView.setOnClickListener {
            Log.d(
                "fish",
                String.format(
                    "%d-%d-%d",
                    dateDetails.weekArray[position][0],
                    dateDetails.weekArray[position][1],
                    dateDetails.weekArray[position][2]
                )
            )

            selectedWeekOffset = 0  // reset, user clicked so current week is now active week
            notifyItemChanged(selectedPos)
            selectedPos = holder.adapterPosition
            notifyItemChanged(selectedPos)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = 7

    fun update(dateDetails: DateDetails) {
        this.dateDetails = dateDetails
        notifyDataSetChanged()
    }
}