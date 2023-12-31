package ict2105.team02.application.ui.equipment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityEditScopeBinding
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.dialogs.ConfirmationDialogFragment
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_BRAND
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_NEXT_SAMPLE_DATE
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_TYPE
import ict2105.team02.application.utils.Utils
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.utils.toDateString
import ict2105.team02.application.viewmodel.EndoscopeViewModel
import kotlinx.coroutines.launch
import java.util.*

class EditScopeActivity : AppCompatActivity() {
    private val endoscopeViewModel: EndoscopeViewModel by viewModels { ViewModelFactory("EndoscopeViewModel", application as MainApplication) }

    private lateinit var binding: ActivityEditScopeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle(R.string.title_edit_endoscope)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityEditScopeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val intentSerial = intent.getIntExtra(KEY_ENDOSCOPE_SERIAL, 0)
        val intentBrand = intent.getStringExtra(KEY_ENDOSCOPE_BRAND)
        val intentModel = intent.getStringExtra(KEY_ENDOSCOPE_MODEL)
        val intentType = intent.getStringExtra(KEY_ENDOSCOPE_TYPE)
        val intentNextSampleDate = intent.getStringExtra(KEY_ENDOSCOPE_NEXT_SAMPLE_DATE)
        val intentStatus = intent.getStringExtra(KEY_ENDOSCOPE_STATUS)

        binding.scopeSerial.setText(intentSerial.toString())
        binding.scopeBrand.setText(intentBrand)
        binding.scopeModel.setText(intentModel)
        binding.scopeType.setText(intentType)
        binding.nextSampleDate.setText(intentNextSampleDate)

        binding.nextSampleDate.setOnClickListener{
            Utils.createMaterialFutureDatePicker(resources.getString(R.string.scope_choose_sample_date)) {
                binding.nextSampleDate.setText(Date(it).toDateString())
            }.show(supportFragmentManager, null)
        }

        binding.buttonUpdateScope.setOnClickListener{
            onUpdateClick(intentSerial, intentStatus)
        }

        binding.buttonDeleteScope.setOnClickListener {
            ConfirmationDialogFragment(resources.getString(R.string.scope_delete_confirm)) {
                // User clicked confirm
                lifecycleScope.launch {
                    endoscopeViewModel.deleteScope(intentSerial)
                }
                Toast.makeText(this, resources.getString(R.string.scope_delete_success), Toast.LENGTH_LONG).show()
                finish()
            }.show(supportFragmentManager, null)
        }
    }

    private fun onUpdateClick(intentSerial: Int, intentStatus: String?) {
        val brand = binding.scopeBrand.text.toString()
        val model = binding.scopeModel.text.toString()
        val type = binding.scopeType.text.toString()
        val nextSampleDate = binding.nextSampleDate.text.toString().parseDateString()

        // Validate fields
        if (brand.isEmpty() || model.isEmpty() || type.isEmpty() || nextSampleDate == null) {
            binding.errorMsg.text = resources.getString(R.string.missing_inputs);
            return
        }

        val confirmationDialog = ConfirmationDialogFragment(resources.getString(R.string.scope_update_confirm)) {
            // User clicked confirm
            // update the details into the database
            lifecycleScope.launch {
                endoscopeViewModel.updateScope(
                    brand,
                    model,
                    intentSerial,
                    type,
                    nextSampleDate,
                    intentStatus!!
                )
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, resources.getString(R.string.scope_update_success), Toast.LENGTH_LONG).show()
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