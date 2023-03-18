package ict2105.team02.application.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ict2105.team02.application.ui.login.LoginActivity
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.viewmodel.AuthViewModel

class NotificationActivity : AppCompatActivity() {
    private val TAG: String = this::class.simpleName!!
    private lateinit var authViewModel: AuthViewModel

    private val intentFragmentSpec: String = "fragmentSpec"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sampleReadyLogic()
    }

    /**
     * Resolves a sample ready notification selection.
     */
    private fun sampleReadyLogic() {
        // connecting to view model
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        if (authViewModel.isUserLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(intentFragmentSpec, getIntent().getStringExtra(intentFragmentSpec))
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}