package ict2105.team02.application.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentHelpBinding
import ict2105.team02.application.model.HelpData

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    private val helpDataList = listOf(
        HelpData("How to use App", "DKvU-5yJo0s", R.array.app_instructions),
        HelpData("Endoscope Cleaning", "SxBjCvnXIeo",R.array.endoscope_cleaning_instructions),
        HelpData("Endoscope Drying", "Sd5xafHAydU",R.array.endoscope_drying_instructions),
        HelpData("Endoscope Sampling", "XyGhlNorlfE",R.array.endoscope_sampling_instructions)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHelpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup recyclerview
        val rvAdapter = HelpAdapter(helpDataList,this)
        binding.helpRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(false)
        }
    }

}