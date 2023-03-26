package ict2105.team02.application.ui.equipment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ict2105.team02.application.R
import ict2105.team02.application.databinding.ActivityEquipmentLogBinding
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_MODEL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_SERIAL
import ict2105.team02.application.utils.Constants.Companion.KEY_ENDOSCOPE_STATUS
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.viewmodel.ScopeDetailViewModel

class EquipmentLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEquipmentLogBinding
    private val scopeDetailViewModel by viewModels<ScopeDetailViewModel>()

    private lateinit var rvAdapter: EquipLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Endoscope Logs"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityEquipmentLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = EquipLogAdapter()
        binding.equipHistoryList.apply {
            layoutManager = LinearLayoutManager(this@EquipmentLogActivity)
            adapter = rvAdapter
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val model = intent.getStringExtra(KEY_ENDOSCOPE_MODEL).toString()
        val serial = intent.getIntExtra(KEY_ENDOSCOPE_SERIAL, -1)
        val status = intent.getStringExtra(KEY_ENDOSCOPE_STATUS).toString()

        binding.modelSerialTextView.text = getString(R.string.endoscope_name, model, serial.toString())
        binding.statusTextView.text = status

        scopeDetailViewModel.scopeLogDetail.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    // nothing to do
                }
                is UiState.Success -> {
                    // uiState Text View
                    binding.uiStateTextView.visibility = View.GONE
                    binding.equipHistoryList.visibility = View.VISIBLE
                    rvAdapter.submitList(it.data)

                }
                is UiState.Error -> {
                    // uiState Text View
                    binding.uiStateTextView.text = it.message
                    binding.uiStateTextView.visibility = View.VISIBLE
                    binding.equipHistoryList.visibility = View.INVISIBLE

                }
            }
        }

        scopeDetailViewModel.fetchLogDetail(serial)
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