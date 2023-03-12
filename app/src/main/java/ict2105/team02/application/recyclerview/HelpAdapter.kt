package ict2105.team02.application.recyclerview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.model.HelpData
import ict2105.team02.application.ui.help.HelpFragment
import ict2105.team02.application.ui.help.YoutubeFragment

class HelpAdapter(private val dataset: List<HelpData>,
                  private val parentFragment: HelpFragment)
    : RecyclerView.Adapter<HelpAdapter.ItemViewHolder>(){
    val TAG = "HelpAdapter"
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
        holder.helpImageButton.setOnClickListener{
            var result : Bundle = Bundle()
            result.putString("videoId",item.VideoID)
            parentFragment.setFragmentResult("helpPage",result)
            val transaction = parentFragment.parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentFrameLayout, YoutubeFragment(),TAG_FRAGMENT)
            transaction.addToBackStack(null);
            transaction.commit()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }




}