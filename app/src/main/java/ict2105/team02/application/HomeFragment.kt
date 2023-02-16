package ict2105.team02.application

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import ict2105.team02.application.databinding.FragmentHomeBinding
import ict2105.team02.application.logout.LogoutFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.logoutFragment->{
                val myDialog = LogoutFragment()
                myDialog.show(childFragmentManager, "MyDialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}