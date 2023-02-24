package ict2105.team02.application.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.schedule.CalendarFragment
import ict2105.team02.application.schedule.DateDetails

class CalendarMonthAdapter(
    private var dateDetails: DateDetails
) : RecyclerView.Adapter<CalendarMonthAdapter.ItemViewHolder>() {
    private val TAG: String = CalendarFragment::class.simpleName!!
    private val maxGridCount: Int = 42
    private val deselectedHex: Int = 0x0F0000FF
    private val selectedHex: Int = 0x7F00FF00

    var selectedPos: Int = dateDetails.day!! + dateDetails.firstDayOfMonth!! - 1
    var selectedMonthOffset : Int = 0
    var onItemClick: ((IntArray) -> Unit)? = null

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewCalendarMonthItem)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarMonthAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_calendar_month_item, parent, false)
        adapterLayout.layoutParams.height = (parent.height * 0.07).toInt()

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CalendarMonthAdapter.ItemViewHolder, position: Int) {
        // Grid item must be equal to larger than starting day and less than number of days + start day
        // Position is 0 terminated, must +1 when displaying
        if (position >= dateDetails.firstDayOfMonth!! && position < dateDetails.firstDayOfMonth!! + dateDetails.daysInMonth!!) {
            holder.textView.text = String.format("%d", position + 1 - dateDetails.firstDayOfMonth!!)
        }
        else{
            return
        }

        // Only highlight day when the user is on the original month
        if (selectedPos == position && selectedMonthOffset == 0) {
            holder.itemView.setBackgroundColor(selectedHex)
        } else {
            holder.itemView.setBackgroundColor(deselectedHex)
        }

        // Attach listener that returns date to Fragment and updates selection
        holder.textView.setOnClickListener {
            onItemClick?.invoke(intArrayOf(holder.textView.text.toString().toInt(), dateDetails.month!!, dateDetails.year!!))

            selectedMonthOffset = 0  // reset, user clicked so current week is now active week
            notifyItemChanged(selectedPos)
            selectedPos = holder.adapterPosition
            notifyItemChanged(selectedPos)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = maxGridCount

    /**
     * Updates stored date details and selected position,
     * then resets the whole thing.
     */
    fun update(dateDetails: DateDetails) {
        this.dateDetails = dateDetails
        selectedPos = dateDetails.day!! + dateDetails.firstDayOfMonth!! - 1

        notifyDataSetChanged()
    }
}