package ict2105.team02.application.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import ict2105.team02.application.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository{
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email:String, password: String) : UiState<AuthResult> {
        return withContext(Dispatchers.IO){
            // use firebase authentication
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                UiState.Success(result)
            } catch (e: Exception){
                UiState.Error("Invalid Email and Password")
            }
        }
    }

}