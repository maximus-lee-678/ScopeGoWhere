package ict2105.team02.application.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.HelpData

class HelpAdapter(private val dataset: List<HelpData>,
                  private val parentFragment: HelpFragment
)
    : RecyclerView.Adapter<HelpAdapter.ItemViewHolder>(){
    private lateinit var helpFragment : Fragment
    class ItemViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        val helpTitle : TextView = view.findViewById(R.id.HelpTitle)
        val helpImageButton : ImageButton = view.findViewById(R.id.HelpImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Get Context From Parents
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.recyclerview_help_item, parent, false)
        return ItemViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val TAG_FRAGMENT = "HelpPage"
        val item = dataset[position]
        holder.helpTitle.text = item.Title
	    helpFragment = HelpPageFragment()
        holder.helpImageButton.setImageResource(getImageResourceId(item))
        holder.helpImageButton.setOnClickListener{
	        val result = Bundle().apply {
		        putString("videoId", item.VideoID)
		        putInt("stringArrayID", item.stringArrayID)
	        }
	        parentFragment.setFragmentResult("helpPage",result)
	        parentFragment.parentFragmentManager.beginTransaction().apply{
	            replace(R.id.fragmentFrameLayout, helpFragment ,TAG_FRAGMENT)
	            addToBackStack(null)
	            commit()
	        }
        }
    }

    private fun getImageResourceId(item : HelpData) : Int {
	    return when (item.Title) {
		    "How to use App" -> R.drawable.guide_icon
		    "Endoscope Cleaning" -> R.drawable.cleaning_icon
		    "Endoscope Drying" -> R.drawable.drying_icon
		    "Endoscope Sampling" -> R.drawable.sampling_icon
		    else -> throw IllegalArgumentException("Invalid title: ${item.Title}")
	    }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}