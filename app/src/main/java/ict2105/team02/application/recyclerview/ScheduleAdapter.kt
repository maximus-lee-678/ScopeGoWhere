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
import ict2105.team02.application.model.Endoscope
import ict2105.team02.application.model.Schedule

class ScheduleAdapter(private val dataset: ArrayList<Endoscope>?)
    : ListAdapter<Schedule, ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffCallBack()) {

        class ScheduleViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
            val ScopeName : TextView = view.findViewById(R.id.ScopeName)
            val ScopeStatus : TextView = view.findViewById(R.id.ScopeStatus)
            val imageButton : ImageButton = view.findViewById(R.id.ScopeImageButton)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        // Get Context From Parents
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView  = inflater.inflate(R.layout.recyclerview_schedule_item,parent,false)
        return ScheduleViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.ScopeName.text = currentItem.scopeModel + " " + currentItem.scopeSerial
        holder.ScopeStatus.text = currentItem.scopeStatus
        holder.imageButton.setOnClickListener{
            Log.d("Schedule" , "Button Test")
        }
    }


}
class ScheduleDiffCallBack : DiffUtil.ItemCallback<Schedule>(){
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem.scopeSerial == newItem.scopeSerial
    }

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }

}