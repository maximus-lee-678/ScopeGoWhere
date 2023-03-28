package ict2105.team02.application.notification

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ict2105.team02.application.repo.MainApplication
import ict2105.team02.application.repo.ViewModelFactory
import ict2105.team02.application.ui.main.LoginActivity
import ict2105.team02.application.ui.main.MainActivity
import ict2105.team02.application.viewmodel.AuthViewModel

class NotificationActivity : AppCompatActivity() {
    private val TAG: String = this::class.simpleName!!
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory("AuthViewModel", application as MainApplication) }

    private val intentFragmentSpec: String = "fragmentSpec"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sampleReadyLogic()
    }

    /**
     * Resolves a sample ready notification selection.
     */
    private fun sampleReadyLogic() {
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