package ict2105.team02.application.ui.equipment

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityAddScopeBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.utils.Utils.Companion.createMaterialFutureDatePicker
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.EndoscopeViewModel
import kotlinx.coroutines.launch
import java.util.*

class AddScopeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddScopeBinding

    private val endoscopeViewModel: EndoscopeViewModel by viewModels { ViewModelFactory("EndoscopeViewModel", application as MainApplication) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.title_create_endoscope)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityAddScopeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Date picker
        binding.nextSampleDate.setOnClickListener{
            createMaterialFutureDatePicker("Choose upcoming sample date") {
                binding.nextSampleDate.setText(Date(it).toDateString())
            }.show(supportFragmentManager, null)
        }

        binding.buttonAddScope.setOnClickListener {
            val brand = binding.scopeBrand.text.toString()
            val model = binding.scopeModel.text.toString()
            val serial = binding.scopeSerial.text.toString().toInt()
            val type = binding.scopeType.text.toString()
            val nextSample = binding.nextSampleDate.text.toString().parseDateString()

            ConfirmationDialogFragment("Add new endoscope?") {
                // User clicked confirm
                lifecycleScope.launch {
                    endoscopeViewModel.insertScope(brand, model, serial, type, nextSample!!)
                }
                finish()
            }.show(supportFragmentManager, null)
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
}