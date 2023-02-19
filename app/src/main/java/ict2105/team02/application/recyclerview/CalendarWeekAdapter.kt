package ict2105.team02.application.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.schedule.CalendarFragment
import ict2105.team02.application.schedule.DateDetails

class CalendarWeekAdapter(
    private var dateDetails: DateDetails
) : RecyclerView.Adapter<CalendarWeekAdapter.ItemViewHolder>() {
    private val TAG: String = CalendarFragment::class.simpleName!!
    private val daysInWeek: Int = 7
    private val deselectedHex: Int = 0x0F0000FF
    private val selectedHex: Int = 0x7F00FF00

    var selectedPos: Int = dateDetails.day!! - dateDetails.weekArray[0][0]
    var selectedWeekOffset: Int = 0
    var onItemClick: ((IntArray) -> Unit)? = null

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
            "[pos%d] %d-%d-%d",
            position,
            dateDetails.weekArray[position][0],
            dateDetails.weekArray[position][1],
            dateDetails.weekArray[position][2]
        )

        if (selectedPos == position && selectedWeekOffset == 0) {
            holder.itemView.setBackgroundColor(selectedHex)
        } else {
            holder.itemView.setBackgroundColor(deselectedHex)
        }

        // Attach listener that returns date to Fragment and updates selection
        holder.textView.setOnClickListener {
            onItemClick?.invoke(dateDetails.weekArray[holder.adapterPosition])

            selectedWeekOffset = 0  // reset, user clicked so current week is now active week
            notifyItemChanged(selectedPos)
            selectedPos = holder.adapterPosition
            notifyItemChanged(selectedPos)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = daysInWeek

    /**
     * Updates stored date details and selected position,
     * then resets the whole thing.
     */
    fun update(dateDetails: DateDetails) {
        this.dateDetails = dateDetails

        // Generate new selected position
        // First day is in this month: Generate position relative to it
        // First day is in prev month: Cycle through array find matching day position
        if (dateDetails.weekArray[0][0]!! < dateDetails.day!!) {
            selectedPos = dateDetails.day!! - dateDetails.weekArray[0][0]
        } else {
            for (i in 0..6) {
                if (dateDetails.weekArray[i][0] == dateDetails.day!!) {
                    selectedPos = i
                    break
                }
            }
        }

        notifyDataSetChanged()
    }
}