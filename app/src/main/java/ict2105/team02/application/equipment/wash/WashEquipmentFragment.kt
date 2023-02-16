package ict2105.team02.application.equipment.wash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import ict2105.team02.application.MainActivity
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityWashEquipmentBinding

class WashEquipmentFragment : Fragment() {
    private lateinit var binding: ActivityWashEquipmentBinding
    private lateinit var checkedTextView: CheckedTextView

    private val sharedViewModel: WashViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityWashEquipmentBinding.inflate(inflater)

        sharedViewModel.done1.observe(viewLifecycleOwner) { done1 ->
            done1?.let {
                binding.checkedTextView.isChecked = it
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkedTextView = view.findViewById(R.id.checkedTextView)
        checkedTextView.setOnClickListener{
            val fragment = ScopeDetailsWashFragment()
            (activity as MainActivity).navbarNavigate(fragment)
        }
    }

    fun setMyCheckedTextView(){
        checkedTextView.toggle()
    }

    fun onClickWasher(){

    }
    fun onClickDisinfectant(){

    }
    fun onClickDetergent(){

    }
    fun onClickDryingCabinet(){

    }


}