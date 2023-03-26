package ict2105.team02.application.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.notification.NotificationSpawner
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.equipment.EquipmentFragment
import ict2105.team02.application.ui.help.HelpFragment
import ict2105.team02.application.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory("AuthViewModel", application as MainApplication) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NotificationSpawner(this).createNotificationChannel()

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

        binding.navbarFab.setOnClickListener {
            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)
        }

        // Default to homepage
        binding.bottomNavbar.selectedItemId = R.id.nav_home
    }

    fun navbarNavigate(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFrameLayout, fragment, null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutFragment -> {
                val confirmationDialog = ConfirmationDialogFragment("Logout?") {
                    authViewModel.logout()
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