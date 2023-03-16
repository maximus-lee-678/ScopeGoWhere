package ict2105.team02.application.recyclerview

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.DateDetails

class CalendarWeekAdapter(private val context: Context) :
    RecyclerView.Adapter<CalendarWeekAdapter.ItemViewHolder>() {
    private val TAG: String = this::class.simpleName!!
    private val daysInWeek: Int = 7
    private val deselectedHex: Int =
        ResourcesCompat.getColor(context.resources, R.color.schedule_unselected, null)
    private val selectedHex: Int =
        ResourcesCompat.getColor(context.resources, R.color.schedule_selected, null)
    private val servicingIcon: String = context.getString(R.string.servicing_icon)
    private val servicingHex: Int =
        ResourcesCompat.getColor(context.resources, R.color.schedule_event_dot, null)

    // initially uninitialised, observer will call updateRecyclerContent to load it
    private var dateDetails: DateDetails? = null
    private var selectedDate: IntArray = intArrayOf()
    private var selectedPos: Int = 0
    private var samplingDates: HashMap<String, Int>? = null

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
        // If called before initialisation, render nothing
        if (dateDetails == null || samplingDates == null) {
            return
        }

        // Getting number of samples on the day by looking up key in hashmap
        val sampleCount: Int? = samplingDates!!.get(
            String.format(
                "%02d-%02d-%04d",
                dateDetails!!.weekArray[position][0],
                dateDetails!!.weekArray[position][1],
                dateDetails!!.weekArray[position][2]
            )
        )

        // Set Text
        val dateText: String =
            String.format(
                "%02d/%02d/%04d",
                dateDetails!!.weekArray[position][0],
                dateDetails!!.weekArray[position][1],
                dateDetails!!.weekArray[position][2]
            )  // Date
        val dateTextSpan = SpannableString(dateText)
        dateTextSpan.setSpan(
            RelativeSizeSpan(1f), 0, dateText.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        val servicingText: String = servicingIcon.repeat(
            sampleCount ?: 0
        )  // Servicing Count
        val servicingTextSpan = SpannableString(servicingText)
        servicingTextSpan.setSpan(
            RelativeSizeSpan(1f),
            0,
            servicingText.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )   // Set size
        servicingTextSpan.setSpan(
            ForegroundColorSpan(servicingHex),
            0,
            servicingText.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )   // Set colour

        holder.textView.text = TextUtils.concat(dateTextSpan, "   ", servicingTextSpan)

        // Only highlight day when the user is on the original month
        if (selectedPos >= 0 && selectedPos == position) {
            holder.itemView.setBackgroundColor(selectedHex)
        } else {
            holder.itemView.setBackgroundColor(deselectedHex)
        }

        // Attach listener that returns date to Fragment and updates selection
        holder.textView.setOnClickListener {
            selectedDate = dateDetails!!.weekArray[holder.bindingAdapterPosition]

            onItemClick?.invoke(selectedDate)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = daysInWeek

    /**
     * Helper function that computes and updates selectedPos.
     * Computes -1 if selected date is off screen, and corresponding position otherwise.
     */
    private fun getSelectedPosition(dateDetails: DateDetails): Boolean {
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

    /**
     * After the user has moved off the original page, the selectedDate is not updated.
     * This function is called to force an update of selectedDate upon selection.
     * Called by observer when a new selected date is detected.
     */
    fun forceUpdateDate(newSelectedDate: IntArray) {
        selectedDate = intArrayOf(newSelectedDate[0], newSelectedDate[1], newSelectedDate[2])
    }

    /**
     * Updates stored date details, then prompts the recyclerView to regenerate as necessary.
     * Called by observer when a new dateDetails object is detected.
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

    /**
     * Updates stored sampling dates, then prompts the recyclerView to regenerate.
     * Called by observer when a new samplingDetails hashmap is detected.
     */
    fun updateSamplingDates(samplingDates: HashMap<String, Int>) {
        this.samplingDates = samplingDates
        notifyDataSetChanged()
    }
}