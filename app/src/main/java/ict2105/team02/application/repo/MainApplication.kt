package ict2105.team02.application.repo

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed, rather than when the application starts
    val userPreferencesRepository by lazy { UserPreferencesRepository.getInstance(this) }
    val authRepository by lazy { AuthRepository.getInstance() }
    val dataRepository by lazy { DataRepository.getInstance() }
}