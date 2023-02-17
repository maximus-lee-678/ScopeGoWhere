package ict2105.team02.application.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentTempScheduleBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [.newInstance] factory method to
 * create an instance of this fragment.
 */
class TempScheduleFragment : Fragment() {
    private lateinit var binding: FragmentTempScheduleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTempScheduleBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        Log.d("onCreateViewTag", "Test1");
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = ScheduleInfoFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.frameLayout.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d("onViewCreatedTag", "Test2");
    }
}