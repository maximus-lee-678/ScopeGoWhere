package ict2105.team02.application.ui.sample


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivitySampleBinding
import ict2105.team02.application.ui.wash.*
import ict2105.team02.application.utils.Constants
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.viewmodel.SampleViewModel

class SampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleBinding
    private val viewModel by viewModels<SampleViewModel>()

    private var step: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val brand = intent.getStringExtra(Constants.KEY_ENDOSCOPE_BRAND)
        val model = intent.getStringExtra(Constants.KEY_ENDOSCOPE_MODEL)
        val serial = intent.getIntExtra(Constants.KEY_ENDOSCOPE_SERIAL, -1)
        if (brand != null && model != null && serial > 0){
            viewModel.makeScope(brand, model, serial)
            Log.d(TAG, "[Wash] Scope detail: $brand $model $serial")
        }


        val totalSteps = 5
        binding.buttonSampleNextStep.setOnClickListener {
            if (++step > totalSteps) step = totalSteps
            changePage(step)

            when (step) {
                2 -> { // Page 1 to 2, enable previous button
                    binding.buttonSamplePreviousStep.visibility = View.VISIBLE
                }
                totalSteps -> { // Hide next button on last page
                    it.visibility = View.INVISIBLE
                }
            }
        }
        binding.buttonSamplePreviousStep.setOnClickListener {
            if (--step < 1) step = 1
            changePage(step)

            when (step) {
                1 -> { // Hide previous button on first page
                    it.visibility = View.INVISIBLE
                }
                totalSteps - 1 -> { // No longer last, enable next button
                    binding.buttonSampleNextStep.visibility = View.VISIBLE
                }
            }
        }

        viewModel.makeSampleData()

        binding.buttonSamplePreviousStep.visibility = View.INVISIBLE
        changePage(step)
    }

    private fun changePage(page: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (page) {
            1 -> transaction.replace(R.id.fragmentSampleFrameLayout, Sample1FluidResultFragment())
            2 -> transaction.replace(R.id.fragmentSampleFrameLayout, Sample2SwabResultFragment())
            3 -> transaction.replace(R.id.fragmentSampleFrameLayout, Sample3RepeatOfMsFragment())
            4 -> transaction.replace(R.id.fragmentSampleFrameLayout, Sample4AtpFragment())
            5 -> transaction.replace(R.id.fragmentSampleFrameLayout, Sample5ReviewFragment())
        }
        transaction.commit()
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
}