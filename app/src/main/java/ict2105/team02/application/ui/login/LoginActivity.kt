package ict2105.team02.application.ui.login

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcB
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.databinding.ActivityLoginBinding
import ict2105.team02.application.viewmodel.LoginViewModel

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private var intentFilters: Array<IntentFilter>? = null
    private val techList = arrayOf(arrayOf(NfcB::class.java.name))
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide title bar
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // connecting to view model
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // nfc adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter != null) {
            // NFC is supported on this device
            val intentNfc = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            pendingIntent = PendingIntent.getActivity(this,0,intentNfc, PendingIntent.FLAG_IMMUTABLE)
            val intentFilter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
            try{
                intentFilter.addDataType("*/*")
                intentFilters = arrayOf(intentFilter)
            } catch (e: Exception){
                Log.e("Login Activity","Error creating intent filter", e)
            }
        }

        //login button validate
        binding.loginButton.setOnClickListener {
            var staffEmail = binding.userName.editText?.text.toString()
            var password = binding.password.editText?.text.toString()
            viewModel.login(staffEmail, password)
        }

        viewModel.loginStatus.observe(this, Observer {
            when(it){
                is UiState.Loading -> {
                    // nothing to do
                }
                is UiState.Success -> {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is UiState.Error -> {
                    binding.error.text = it.message
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.let {
            it.enableForegroundDispatch(this, pendingIntent, intentFilters, techList)
        }
    }

    override fun onPause() {
        if (this.isFinishing){
            nfcAdapter?.disableForegroundDispatch(this)
        }
        super.onPause()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "Read a NFC", Toast.LENGTH_LONG).show()
        var homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}