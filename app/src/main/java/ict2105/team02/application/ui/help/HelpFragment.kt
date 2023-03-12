package ict2105.team02.application.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ict2105.team02.application.databinding.FragmentHelpItemListBinding
import ict2105.team02.application.recyclerview.ScheduleAdapter
import ict2105.team02.application.viewmodel.ScheduleInfoViewModel

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpItemListBinding
    private lateinit var adapter: ScheduleAdapter
    private lateinit var recyclerView: RecyclerView
    //    private val sharedViewModel: WashViewModel by activityViewModels()
    private lateinit var viewModel: ScheduleInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpItemListBinding.inflate(layoutInflater)
        val view: View = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}