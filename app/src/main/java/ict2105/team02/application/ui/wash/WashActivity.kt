package ict2105.team02.application.ui.wash

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityWashBinding
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_BRAND
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.TAG
import ict2105.team02.application.viewmodel.WashViewModel

class WashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWashBinding
    private val viewModel by viewModels<WashViewModel>()

    private var step: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val brand = intent.getStringExtra(KEY_ENDOSCOPE_BRAND)
        val model = intent.getStringExtra(KEY_ENDOSCOPE_MODEL)
        val serial = intent.getIntExtra(KEY_ENDOSCOPE_SERIAL, -1)
        if (brand != null && model != null && serial > 0){
            viewModel.makeScope(brand, model, serial)
            Log.d(TAG, "[Wash] Scope detail: $brand $model $serial")
        }

        val totalSteps = 6
        binding.buttonWashNextStep.setOnClickListener {
            if (++step > totalSteps) step = totalSteps
            changePage(step)

            when (step) {
                2 -> { // Page 1 to 2, enable previous button
                    binding.buttonWashPreviousStep.visibility = View.VISIBLE
                }
                totalSteps -> { // Hide next button on last page
                    it.visibility = View.INVISIBLE
                }
            }
        }
        binding.buttonWashPreviousStep.setOnClickListener {
            if (--step < 1) step = 1
            changePage(step)

            when (step) {
                1 -> { // Hide previous button on first page
                    it.visibility = View.INVISIBLE
                }
                totalSteps - 1 -> { // No longer last, enable next button
                    binding.buttonWashNextStep.visibility = View.VISIBLE
                }
            }
        }

        viewModel.makeWashData()

        binding.buttonWashPreviousStep.visibility = View.INVISIBLE
        changePage(step)
    }

    private fun changePage(page: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (page) {
            1 -> transaction.replace(R.id.fragmentWashFrameLayout, Wash1ScopeDetailFragment())
            2 -> transaction.replace(R.id.fragmentWashFrameLayout, Wash2WasherFragment())
            3 -> transaction.replace(R.id.fragmentWashFrameLayout, Wash3DetergentFragment())
            4 -> transaction.replace(R.id.fragmentWashFrameLayout, Wash4DisinfectantFragment())
            5 -> transaction.replace(R.id.fragmentWashFrameLayout, Wash5DryingCabinetFragment())
            6 -> transaction.replace(R.id.fragmentWashFrameLayout, Wash6ReviewFragment())
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