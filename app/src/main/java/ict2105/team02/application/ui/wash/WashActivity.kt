package ict2105.team02.application.ui.wash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.dialogs.ScopeDetailFragment
import ict2105.team02.application.ui.main.EquipmentFragment
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.ui.main.HomeFragment
import ict2105.team02.application.ui.main.QRScannerActivity
import ict2105.team02.application.viewmodel.WashViewModel

class WashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WashViewModel::class.java]
        val fragment = ScopeDetailsWashFragment()
        var scopeDetailsMap = HashMap<String, Any>()
        if (intent.extras != null){
            scopeDetailsMap = intent.getSerializableExtra("scopeDetails") as HashMap<String, Any>
            viewModel.makeScope(scopeDetailsMap["scopeBrand"].toString(), scopeDetailsMap["scopeModel"].toString(), scopeDetailsMap["scopeSerial"]as Int)
        }
        navbarNavigate(fragment)

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