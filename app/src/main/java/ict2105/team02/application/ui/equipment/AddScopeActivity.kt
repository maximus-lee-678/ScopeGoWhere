package ict2105.team02.application.ui.equipment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ict2105.team02.application.databinding.ActivityAddScopeBinding
import ict2105.team02.application.utils.parseDateString
import ict2105.team02.application.viewmodel.NewScopeViewModel
import kotlinx.coroutines.launch

class AddScopeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddScopeBinding
    private val viewModel by viewModels<NewScopeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddScopeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.buttonAddScope.setOnClickListener {
            val brand = binding.scopeBrand.editText?.text.toString()
            val model = binding.scopeModel.editText?.text.toString()
            val serial = binding.scopeSerial.editText?.text.toString().toInt()
            val type = binding.scopeType.editText?.text.toString()
            val nextSample = binding.nextSampleDate.editText?.text.toString().parseDateString()
            lifecycleScope.launch {
                viewModel.insertScope(brand,model,serial,type,nextSample!!)
            }
            finish()
        }
    }
}