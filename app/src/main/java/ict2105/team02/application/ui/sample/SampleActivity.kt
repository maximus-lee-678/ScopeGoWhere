package ict2105.team02.application.ui.sample


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ict2105.team02.application.databinding.ActivitySampleBinding
import ict2105.team02.application.ui.wash.WashActivity
import ict2105.team02.application.utils.Constants
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.viewmodel.SampleViewModel

class SampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleBinding
    private val viewModel by viewModels<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2 provides swipe function, TabLayout provides tabs
        val stateAdapter = SampleFragmentStateAdapter(this)
        binding.viewPagerSample.apply {
            adapter = stateAdapter
            TabLayoutMediator(binding.tabLayoutSample, this) { tab, position ->
                when (position) {
                    0 -> tab.text = "1. Fluid"
                    1 -> tab.text = "2. Swab"
                    2 -> tab.text = "3. Repeat MS"
                    3 -> tab.text = "4. ATP"
                    4 -> tab.text = "5. Summary"
                }
            }.attach()
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    title = "Sample Equipment (${position + 1}/5)"
                    updateNavigationButtons(position)
                }
            })
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val brand = intent.getStringExtra(Constants.KEY_ENDOSCOPE_BRAND)
        val model = intent.getStringExtra(Constants.KEY_ENDOSCOPE_MODEL)
        val serial = intent.getIntExtra(Constants.KEY_ENDOSCOPE_SERIAL, -1)
        if (brand != null && model != null && serial > 0){
            viewModel.makeScope(brand, model, serial)
            Log.d(TAG, "[Wash] Scope detail: $brand $model $serial")
        }


        binding.buttonSampleNextStep.setOnClickListener { changePage(binding.viewPagerSample.currentItem + 1) }
        binding.buttonSamplePreviousStep.setOnClickListener { changePage(binding.viewPagerSample.currentItem - 1) }

        viewModel.makeSampleData()
        binding.buttonSamplePreviousStep.visibility = View.INVISIBLE
        title = "Sample Equipment (1/5)"
        changePage(0)
    }

    private fun changePage(page: Int) {
        var newPage = page
        if (newPage > TOTAL_STEPS - 1) newPage = TOTAL_STEPS - 1
        else if (newPage < 0) newPage = 0

        binding.tabLayoutSample.getTabAt(newPage)?.select()
        binding.viewPagerSample.currentItem = newPage
    }

    private fun updateNavigationButtons(newPage: Int) {
        when (newPage) {
            0 -> {
                // Hide previous button on first page
                binding.buttonSamplePreviousStep.visibility = View.INVISIBLE
            }
            1 -> {
                // No longer first page, enable previous button
                binding.buttonSamplePreviousStep.visibility = View.VISIBLE
            }
            TOTAL_STEPS - 2 -> {
                // No longer last page, enable next button
                binding.buttonSampleNextStep.visibility = View.VISIBLE
            }
            TOTAL_STEPS - 1 -> {
                // Hide next button on last page
                binding.buttonSampleNextStep.visibility = View.INVISIBLE
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