package ict2105.team02.application.ui.equipment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityAddScopeBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.ui.main.MainActivity
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
            createMaterialFutureDatePicker(resources.getString(R.string.scope_choose_sample_date)) {
                binding.nextSampleDate.setText(Date(it).toDateString())
            }.show(supportFragmentManager, null)
        }

        binding.buttonAddScope.setOnClickListener {
            onAddClick()
        }
    }

    private fun onAddClick() {
        val brand = binding.scopeBrand.text.toString()
        val model = binding.scopeModel.text.toString()
        val serial = binding.scopeSerial.text.toString()
        val type = binding.scopeType.text.toString()
        val nextSample = binding.nextSampleDate.text.toString().parseDateString()

        // Validate fields
        if (brand.isEmpty() || model.isEmpty() || serial.isEmpty()|| type.isEmpty() || nextSample == null) {
            binding.errorMsg.text = resources.getString(R.string.missing_inputs);
            return
        }

        val confirmationDialog = ConfirmationDialogFragment(resources.getString(R.string.scope_add_confirm)) {
            // User clicked confirm
            // update the details into the database
            lifecycleScope.launch {
                endoscopeViewModel.insertScope(brand, model, serial.toInt(), type, nextSample)
            }
            Toast.makeText(this, resources.getString(R.string.scope_add_success), Toast.LENGTH_LONG).show()
            finish()
        }
        confirmationDialog.show(supportFragmentManager, "ConfirmationDialog")
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