package ict2105.team02.application.ui.sample

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityMainBinding
import ict2105.team02.application.ui.main.EquipmentFragment
import ict2105.team02.application.ui.main.HomeFragment
import ict2105.team02.application.ui.main.QRScannerActivity
import ict2105.team02.application.ui.schedule.ScheduleFragment
import ict2105.team02.application.viewmodel.NFCViewModel

class SampleActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nfcViewModel: NFCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Sample Acitivty", "Created")
        navbarNavigate(SampleMethodFragment())

        binding.bottomNavbar.setOnItemSelectedListener{
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

    override fun onTagDiscovered(tag: Tag) {
        nfcViewModel.readTag(tag)
    }
}