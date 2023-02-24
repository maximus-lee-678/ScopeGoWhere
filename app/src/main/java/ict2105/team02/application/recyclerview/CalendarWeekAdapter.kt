package ict2105.team02.application.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.DateDetails
import ict2105.team02.application.schedule.CalendarFragment

class CalendarWeekAdapter(
    private var dateDetails: DateDetails
) : RecyclerView.Adapter<CalendarWeekAdapter.ItemViewHolder>() {
    private val TAG: String = CalendarFragment::class.simpleName!!
    private val daysInWeek: Int = 7
    private val deselectedHex: Int = 0x0F0000FF
    private val selectedHex: Int = 0x7F00FF00

    var selectedDate: IntArray =
        intArrayOf(dateDetails.day!!, dateDetails.month!!, dateDetails.year!!)
    var selectedPos: Int = dateDetails.day!! - dateDetails.weekArray[0][0]
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
            "%d-%d-%d",
            dateDetails.weekArray[position][0],
            dateDetails.weekArray[position][1],
            dateDetails.weekArray[position][2]
        )

        if (selectedPos >= 0 && selectedPos == position) {
            holder.itemView.setBackgroundColor(selectedHex)
        } else {
            holder.itemView.setBackgroundColor(deselectedHex)
        }

        // Attach listener that returns date to Fragment and updates selection
        holder.textView.setOnClickListener {
            selectedDate = dateDetails.weekArray[holder.adapterPosition]

            onItemClick?.invoke(selectedDate)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = daysInWeek

    fun getSelectedPosition(dateDetails: DateDetails): Boolean {

        // if matching date is found in week array, selected position is on screen
        for (i in 0..6) {
            if (dateDetails.weekArray[i][0] == selectedDate[0] && dateDetails.weekArray[i][1] == selectedDate[1] && dateDetails.weekArray[i][2] == selectedDate[2]) {
                selectedPos = i
                return false
            }
        }

        // no match found, selected position is off screen
        selectedPos = -1
        return true
    }

    // After the user has moved off the original page, the selectedDate is not updated.
    // This function is called to force an update of selectedDate upon selection.
    fun forceUpdateDate(newSelectedDate: IntArray) {
        selectedDate = intArrayOf(newSelectedDate[0], newSelectedDate[1], newSelectedDate[2])
    }

    /**
     * Updates stored date details and selected position,
     * then resets the whole thing.
     */
    fun updateRecyclerContent(dateDetails: DateDetails) {
        // Update dateDetails
        this.dateDetails = dateDetails

        val originalPosition: Int = selectedPos

        // Generate new position
        val didPageChange: Boolean = getSelectedPosition(dateDetails)
        // User is returning to original page, selected pos goes from negative to positive
        // Refresh whole set
        if (!didPageChange && originalPosition == -1) {
            notifyDataSetChanged()
            return
        }

        // User is clicking on another date on original page
        // Refresh only 2 items
        if (!didPageChange) {
            notifyItemChanged(originalPosition)
            notifyItemChanged(selectedPos)
            return
        }

        // User is leaving original page
        // Refresh whole set
        if (selectedPos == -1) {
            notifyDataSetChanged()
            return
        }

    }
}