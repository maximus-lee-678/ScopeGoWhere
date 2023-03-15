package ict2105.team02.application.recyclerview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.DateDetails
import java.io.ByteArrayOutputStream



class CalendarMonthAdapter(private val context: Context) :
    RecyclerView.Adapter<CalendarMonthAdapter.ItemViewHolder>() {
    private val TAG: String = this::class.simpleName!!
    private val maxGridCount: Int = 42
    private val cellHeight: Double = 0.075
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
        adapterLayout.layoutParams.height = (parent.height * cellHeight).toInt()

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CalendarMonthAdapter.ItemViewHolder, position: Int) {
        // If called before initialisation, render nothing
        if (dateDetails == null || samplingDates == null) {
            return
        }

        // Grid item must be equal to larger than starting day and less than number of days + start day
        // Position is 0 terminated, must +1 when calculating
        if (position + 1 <= dateDetails!!.firstDayOfMonth!! || position + 1 > dateDetails!!.firstDayOfMonth!! + dateDetails!!.daysInMonth!!) {
            return
        }

        // Getting number of samples on the day by looking up key in hashmap
        val sampleCount: Int? = samplingDates!!.get(
            String.format(
                "%02d-%02d-%04d",
                position + 1 - dateDetails!!.firstDayOfMonth!!,
                dateDetails!!.month!!,
                dateDetails!!.year!!
            )
        )

        // Set Text
        val dateText: String =
            (position + 1 - dateDetails!!.firstDayOfMonth!!).toString()  // Day number
        val dateTextSpan = SpannableString(dateText)
        dateTextSpan.setSpan(RelativeSizeSpan(1f), 0, dateText.length, SPAN_INCLUSIVE_INCLUSIVE)

        val servicingText: String = servicingIcon.repeat(
            sampleCount ?: 0
        )  // Servicing Count, maximum displayable: 4 before funky looking
        val servicingTextSpan = SpannableString(servicingText)
        servicingTextSpan.setSpan(
            RelativeSizeSpan(1f),
            0,
            servicingText.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )   // Set size
        servicingTextSpan.setSpan(
            ForegroundColorSpan(servicingHex),
            0,
            servicingText.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )   // Set colour

        holder.textView.text = TextUtils.concat(dateTextSpan, "\n", servicingTextSpan)

        // Only highlight day when the user is on the original month
        if (selectedPos >= 0 && selectedPos == position) {
            holder.itemView.setBackgroundColor(selectedHex)
        } else {
            holder.itemView.setBackgroundColor(deselectedHex)
        }

        // Attach listener that returns date to Fragment and updates selection
        holder.textView.setOnClickListener {
            selectedDate = intArrayOf(
                position + 1 - dateDetails!!.firstDayOfMonth!!,
                dateDetails!!.month!!,
                dateDetails!!.year!!
            )

            onItemClick?.invoke(selectedDate)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = maxGridCount

    /**
     * Helper function that computes and updates selectedPos.
     * Computes -1 if selected date is off screen, and corresponding position otherwise.
     */
    private fun getSelectedPosition(dateDetails: DateDetails): Boolean {
        // if month & year dont match, page has changed, selected position is off screen
        if (dateDetails.month!! != selectedDate[1] || dateDetails.year!! != selectedDate[2]) {
            selectedPos = -1
            return true
        }

        // page has not changed, calculate position
        selectedPos = dateDetails.day!! + dateDetails.firstDayOfMonth!! - 1
        return false
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
        // Refresh only 2 items (currently causing weird phantoms, disabled)
        if (!didPageChange) {
//            notifyItemChanged(originalPosition)
//            notifyItemChanged(selectedPos)
            notifyDataSetChanged()
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