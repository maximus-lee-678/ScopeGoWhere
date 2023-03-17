package ict2105.team02.application.ui.login

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.R
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.databinding.ActivityLoginBinding
import ict2105.team02.application.viewmodel.AuthViewModel
import ict2105.team02.application.viewmodel.NFCViewModel

class LoginActivity: AppCompatActivity(), NfcAdapter.ReaderCallback {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var nfcViewModel: NFCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide title bar
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // connecting to view model
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        nfcViewModel = ViewModelProvider(this)[NFCViewModel::class.java]

        // enable NFC Reader
        if(nfcViewModel.checkEnabled(this@LoginActivity)) {
            nfcViewModel.enableReaderMode(this@LoginActivity, this@LoginActivity, this@LoginActivity)
            nfcViewModel.observeTag().observe(this) {
                if (it) {
                    nfcViewModel.disableReaderMode(this@LoginActivity, this@LoginActivity)
                    goToHome()
                } else{
                    binding.error.text = getString(R.string.incorrect_login)
                }
            }
        } else {
            Toast.makeText(this, "NFC is not enabled/unavailable", Toast.LENGTH_LONG).show()
        }

        //login button validate
        binding.loginButton.setOnClickListener {
            var staffEmail = binding.userName.editText?.text.toString()
            var password = binding.password.editText?.text.toString()
            authViewModel.login(staffEmail, password)
        }

        authViewModel.loginStatus.observe(this, Observer {
            when(it){
                is UiState.Loading -> {
                    // nothing to do
                }
                is UiState.Success -> {
                    goToHome()
                }
                is UiState.Error -> {
                    binding.error.text = it.message
                }
            }
        })
    }

    override fun onTagDiscovered(tag: Tag) {
        nfcViewModel.readTag(tag)
    }

    private fun goToHome(){
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}