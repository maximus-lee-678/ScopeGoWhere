package ict2105.team02.application.ui.wash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.main.EquipmentFragment
import ict2105.team02.application.ui.main.ScheduleFragment
import ict2105.team02.application.ui.main.HomeFragment
import ict2105.team02.application.ui.main.QRScannerActivity

class WashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbarNavigate(ScopeDetailsWashFragment())

        binding.bottomNavbar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> navbarNavigate(HomeFragment())
                R.id.nav_schedule -> navbarNavigate(ScheduleFragment())
                R.id.nav_equipment -> navbarNavigate(EquipmentFragment())
//                R.id.nav_help -> navbarNavigate(HelpFragment())
                else -> { }
            }
            return@setOnItemSelectedListener true
        }

        binding.navbarFab.setOnClickListener {
            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)
        }
    }

    fun navbarNavigate(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFrameLayout, fragment)
        transaction.commit()
    }

}