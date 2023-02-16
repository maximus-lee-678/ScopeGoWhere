package ict2105.team02.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.login.LoginActivity
import ict2105.team02.application.logout.LogoutFragment
import ict2105.team02.application.ui.EquipmentFragment

class MainActivity : AppCompatActivity(), LogoutFragment.LogoutListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbarNavigate(HomeFragment())

        binding.bottomNavbar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> navbarNavigate(HomeFragment())
//                R.id.nav_schedule -> navbarNavigate(ScheduleFragment())
                R.id.nav_equipment -> navbarNavigate(EquipmentFragment())
//                R.id.nav_help -> navbarNavigate(HomeFragment())
                else -> { }
            }
            return@setOnItemSelectedListener true
        }

        binding.navbarFab.setOnClickListener {
            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navbarNavigate(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    override fun onLogout(result: Boolean) {
        if(result){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}