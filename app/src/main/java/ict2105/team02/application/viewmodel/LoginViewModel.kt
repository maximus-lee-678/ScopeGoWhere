package ict2105.team02.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import ict2105.team02.application.utils.UiState
import ict2105.team02.application.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel(){
    private val authRepository = AuthRepository()

    // make login status to be editable inside View Model and readable to outside
    // create observer
    private val _loginStatus = MutableLiveData<UiState<AuthResult>>()
    val loginStatus: LiveData<UiState<AuthResult>> = _loginStatus

    fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _loginStatus.postValue(UiState.Error("Empty email or password"))
        }else{
            _loginStatus.postValue(UiState.Loading())
            viewModelScope.launch (Dispatchers.Main){
                // run the login function in authRepository and change the live data value
                val result = authRepository.login(email, password)
                _loginStatus.postValue(result)
            }
        }
    }

}