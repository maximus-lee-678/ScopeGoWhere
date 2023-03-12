package ict2105.team02.application.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentHelpBinding
import ict2105.team02.application.model.HelpData
import ict2105.team02.application.recyclerview.HelpAdapter
import ict2105.team02.application.recyclerview.ScheduleAdapter

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding
    private lateinit var adapter: HelpAdapter
    private lateinit var recyclerView: RecyclerView
    val helpDataList = listOf(
        HelpData("How to use App", "DKvU-5yJo0s"),
        HelpData("EndoscopeCleaning", "SxBjCvnXIeo"),
        HelpData("EndoscopeDrying", "Sd5xafHAydU"),
        HelpData("Report Bug", "Sd5xafHAydU")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(layoutInflater)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        // Find recycler view
        recyclerView = view.findViewById(R.id.scheduleRecycle)
        // Set layout
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(false)
        adapter = HelpAdapter(helpDataList,this)
        recyclerView.adapter = adapter
    }

}