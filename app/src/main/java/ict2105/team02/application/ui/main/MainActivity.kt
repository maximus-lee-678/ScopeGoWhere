package ict2105.team02.application.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.login.LoginActivity
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.ui.dialogs.LogoutFragment
import ict2105.team02.application.ui.scopeStore.AddScopeFragment

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

    override fun onLogout(result: Boolean) {
        if(result){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutFragment -> {
                val myDialog = LogoutFragment()
                myDialog.show(supportFragmentManager, "MyDialog")
                return true
            }
            R.id.addScope-> {
                navbarNavigate(AddScopeFragment())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}