package ict2105.team02.application.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.ui.login.LoginActivity
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.ui.scopeStore.AddScopeFragment
import ict2105.team02.application.ui.help.HelpFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG_FRAGMENT = "HomeFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbarNavigate(HomeFragment())

        binding.bottomNavbar.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.nav_home -> navbarNavigate(HomeFragment())
                R.id.nav_schedule -> navbarNavigate(ScheduleFragment())
                R.id.nav_equipment -> navbarNavigate(EquipmentFragment())
                R.id.nav_help -> navbarNavigate(HelpFragment())
                else -> {}
            }
            return@setOnItemSelectedListener true
        }
//        var callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (supportFragmentManager.backStackEntryCount == 0) {
//                    // If we are on the home fragment, exit the app
//                    finish()
//                } else {
//                    // Otherwise, navigate back to the previous fragment
//                    supportFragmentManager.popBackStack()
//                }
//            }
//        }
//
//        // Add the callback to the activity's OnBackPressedDispatcher
//        onBackPressedDispatcher.addCallback(this, callback)

        binding.navbarFab.setOnClickListener {
            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)
        }
    }

    fun navbarNavigate(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFrameLayout, fragment,TAG_FRAGMENT)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutFragment -> {
                val confirmationDialog = ConfirmationDialogFragment("Logout?") {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                confirmationDialog.show(supportFragmentManager, "ConfirmationDialog")
                return true
            }
            R.id.addScope -> {
                navbarNavigate(AddScopeFragment())
                return true
            }
            else -> return true
        }
    }
}