package ict2105.team02.application.ui.wash

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityWashBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_BRAND
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.viewmodel.WashViewModel

class WashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWashBinding
    private val washViewModel: WashViewModel by viewModels {
        ViewModelFactory("WashViewModel", application as MainApplication)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val washHeaderOptions = resources.getStringArray(R.array.wash_menu_opts)
        // ViewPager2 provides swipe function, TabLayout provides tabs
        val stateAdapter = WashFragmentStateAdapter(this)
        binding.viewPagerWash.apply {
            adapter = stateAdapter
            TabLayoutMediator(binding.tabLayoutWash, this) { tab, position ->
                when (position) {
                    0 -> tab.text = washHeaderOptions[0]
                    1 -> tab.text = washHeaderOptions[1]
                    2 -> tab.text = washHeaderOptions[2]
                    3 -> tab.text = washHeaderOptions[3]
                    4 -> tab.text = washHeaderOptions[4]
                }
            }.attach()
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    title = resources.getString(R.string.wash_title_printf, position.toString())
                    updateNavigationButtons(position)
                }
            })
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val brand = intent.getStringExtra(KEY_ENDOSCOPE_BRAND)
        val model = intent.getStringExtra(KEY_ENDOSCOPE_MODEL)
        val serial = intent.getIntExtra(KEY_ENDOSCOPE_SERIAL, -1)
        if (brand != null && model != null && serial > 0){
            washViewModel.makeScope(brand, model, serial)
            Log.d(TAG, "[Wash] Scope detail: $brand $model $serial")
        }

        binding.buttonWashNextStep.setOnClickListener { changePage(binding.viewPagerWash.currentItem + 1) }
        binding.buttonWashPreviousStep.setOnClickListener { changePage(binding.viewPagerWash.currentItem - 1) }

        washViewModel.makeWashData()
        title = resources.getString(R.string.wash_title_printf, "1")
        binding.buttonWashPreviousStep.visibility = View.INVISIBLE
        changePage(0)
    }

    private fun changePage(page: Int) {
        var newPage = page
        if (newPage > TOTAL_STEPS - 1) newPage = TOTAL_STEPS - 1
        else if (newPage < 0) newPage = 0

        binding.tabLayoutWash.getTabAt(newPage)?.select()
        binding.viewPagerWash.currentItem = newPage
    }

    private fun updateNavigationButtons(newPage: Int) {
        when (newPage) {
            0 -> {
                // Hide previous button on first page
                binding.buttonWashPreviousStep.visibility = View.INVISIBLE
            }
            1 -> {
                // No longer first page, enable previous button
                binding.buttonWashPreviousStep.visibility = View.VISIBLE
            }
            TOTAL_STEPS - 2 -> {
                // No longer last page, enable next button
                binding.buttonWashNextStep.visibility = View.VISIBLE
            }
            TOTAL_STEPS - 1 -> {
                // Hide next button on last page
                binding.buttonWashNextStep.visibility = View.INVISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Back button press on action bar
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TOTAL_STEPS = 5
    }
}