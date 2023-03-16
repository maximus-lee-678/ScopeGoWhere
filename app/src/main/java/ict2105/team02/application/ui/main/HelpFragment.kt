package ict2105.team02.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.databinding.FragmentHelpBinding
import ict2105.team02.application.model.HelpData
import ict2105.team02.application.recyclerview.HelpAdapter

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    private val helpDataList = listOf(
        HelpData("How to use App", "DKvU-5yJo0s"),
        HelpData("Endoscope Cleaning", "SxBjCvnXIeo"),
        HelpData("Endoscope Drying", "Sd5xafHAydU"),
        HelpData("Endoscope Sampling", "XyGhlNorlfE")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHelpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup recyclerview
        val rvAdapter = HelpAdapter(helpDataList,this)
        binding.scheduleRecycle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(false)
        }
    }

}