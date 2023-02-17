package ict2105.team02.application.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.Schedule

class ScheduleAdapter(private val dataset: ArrayList<Schedule>?)
    : ListAdapter<Schedule, ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffCallBack()) {

        class ScheduleViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        val ScopeName : TextView = view.findViewById(R.id.ScopeName)
        val imageButton : ImageButton = view.findViewById(R.id.ScopeImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        // Get Context From Parents
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView  = inflater.inflate(R.layout.schedule_item,parent,false)
        return ScheduleViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = dataset?.getOrNull(position)
        if (item != null) {
            holder.ScopeName.text = item.ScopeName
        };
        holder.imageButton.setOnClickListener{
            Log.d("Schedule" , "Button Test")
        }
    }


}
class ScheduleDiffCallBack : DiffUtil.ItemCallback<Schedule>(){
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }

}