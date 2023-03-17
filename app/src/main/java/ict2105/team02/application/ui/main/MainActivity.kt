package ict2105.team02.application.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.ui.login.LoginActivity
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.notification.NotificationSpawner
import ict2105.team02.application.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG: String = this::class.simpleName!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationSpawner(this).createNotificationChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chooseFragment()

        binding.bottomNavbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    navbarNavigate(HomeFragment())
                    supportActionBar?.setTitle(R.string.title_home)
                }
                R.id.nav_schedule -> {
                    navbarNavigate(ScheduleFragment())
                    supportActionBar?.setTitle(R.string.title_schedule)
                }
                R.id.nav_equipment -> {
                    navbarNavigate(EquipmentFragment())
                    supportActionBar?.setTitle(R.string.title_equipment)
                }
                R.id.nav_help -> {
                    navbarNavigate(HelpFragment())
                    supportActionBar?.setTitle(R.string.title_help)
                }
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
        transaction.replace(R.id.fragmentFrameLayout, fragment, null)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Chooses selected fragment based on intent extra value. Defaults to Home.
     */
    private fun chooseFragment() {
        when (intent.getStringExtra(getString(R.string.main_activity_intent_fragment))) {
            "home" -> {
                navbarNavigate(HomeFragment())
            }
            "schedule" -> {
                navbarNavigate(ScheduleFragment())
                binding.bottomNavbar.selectedItemId = R.id.nav_schedule
            }
            "equipment" -> {
                navbarNavigate(EquipmentFragment())
                binding.bottomNavbar.selectedItemId = R.id.nav_equipment
            }
            "help" -> {
                navbarNavigate(HelpFragment())
            }
            null -> {
                navbarNavigate(HomeFragment())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutFragment -> {
                val confirmationDialog = ConfirmationDialogFragment("Logout?") {
                    AuthViewModel().logout()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                confirmationDialog.show(supportFragmentManager, "ConfirmationDialog")
                true
            }
            R.id.notification -> {
                NotificationSpawner(this).generateSampleReadyNotification()
                true
            }
            else -> true
        }
    }
}