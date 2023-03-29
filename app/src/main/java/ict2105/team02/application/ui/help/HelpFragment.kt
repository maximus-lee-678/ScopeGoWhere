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
import ict2105.team02.application.repo.HelpRepo

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding
	private lateinit var helpRepo : HelpRepo
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHelpBinding.inflate(layoutInflater)
	    helpRepo = HelpRepo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup recyclerview
        val rvAdapter = HelpAdapter(helpRepo.getHelpList(),this)
        binding.helpRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(false)
        }
    }

}