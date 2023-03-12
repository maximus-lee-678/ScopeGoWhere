package ict2105.team02.application.recyclerview

import android.graphics.drawable.Drawable
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
import ict2105.team02.application.ui.help.*

class HelpAdapter(private val dataset: List<HelpData>,
                  private val parentFragment: HelpFragment)
    : RecyclerView.Adapter<HelpAdapter.ItemViewHolder>(){
    val TAG = "HelpAdapter"
    private lateinit var fragment : Fragment
    private var resID = 0
    class ItemViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        val helpTitle : TextView = view.findViewById(R.id.HelpTitle)
        val helpImageButton : ImageButton = view.findViewById(R.id.HelpImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Get Context From Parents
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView  = inflater.inflate(R.layout.recyclerview_help_item,parent,false)
        return ItemViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val TAG_FRAGMENT = "HelpPage"
        val item = dataset[position]
        holder.helpTitle.text = item.Title
        changeFragmentAndResID(item)
        holder.helpImageButton.setImageResource(resID)
        holder.helpImageButton.setOnClickListener{
            var result : Bundle = Bundle()
            result.putString("videoId",item.VideoID)
            parentFragment.setFragmentResult("helpPage",result)
            val transaction = parentFragment.parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentFrameLayout, fragment ,TAG_FRAGMENT)
            transaction.addToBackStack(null);
            transaction.commit()
        }
    }
    fun changeFragmentAndResID(item : HelpData) {
        when(item.Title) {
            "How to use App" -> {
                fragment = HowToUseAppFragment()
                resID = R.drawable.bulb_icon
            }
            "Endoscope Cleaning" ->
            {
                fragment = EndoscopeCleaningFragment()
                resID = R.drawable.cleaning
            }
            "Endoscope Drying" ->
            {
                fragment = EndoscopeDryingFragment()
                resID = R.drawable.air_drying_icon
            }
            "Endoscope Sampling" -> {
                fragment = EndoscopeSamplingFragment()
                resID = R.drawable.sampling
            }
            else -> throw IllegalArgumentException("Invalid title: $item.Title")
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }




}