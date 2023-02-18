package ict2105.team02.application.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.schedule.DateDetails

class CalendarMonthAdapter(
    private var dateDetails: DateDetails
) : RecyclerView.Adapter<CalendarMonthAdapter.ItemViewHolder>() {
    var selectedPos: Int = dateDetails.day!! + dateDetails.firstDayOfMonth!! - 1
    var selectedMonthOffset : Int = 0

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewCalendarMonthItem)

        init {
            textView.setOnClickListener {
                if (textView.text == "") {
                    return@setOnClickListener
                }

                selectedMonthOffset = 0         // reset, user clicked so current month is now active month
                notifyItemChanged(selectedPos)
                selectedPos = layoutPosition
                notifyItemChanged(selectedPos)

                Log.d(
                    "fish",
                    textView.text.toString() + "-" + dateDetails.month + "-" + dateDetails.year
                )
            }
        }
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
        adapterLayout.layoutParams.height = (parent.height * 0.08).toInt()

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

        if (selectedPos == position && selectedMonthOffset == 0) {
            holder.itemView.setBackgroundColor(0x7F00FF00.toInt())
        } else {
            holder.itemView.setBackgroundColor(0x00FFFFFF.toInt())
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = 42

    fun update(dateDetails: DateDetails) {
        this.dateDetails = dateDetails
        notifyDataSetChanged()
    }
}