package ict2105.team02.application.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ict2105.team02.application.MainActivity
import ict2105.team02.application.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide title bar
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //login button validate (staffId == admin // password == 1234)
        binding.loginButton.setOnClickListener {
            var staffID = binding.userName.editText?.text.toString()
            var password = binding.password.editText?.text.toString()
            var valid: Boolean = validateLogin(staffID, password)
            if(valid || true) {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                binding.error.text = "Invalid Staff ID or Password"
            }
        }
    }

    //validate login
    //to be validate using database
    private fun validateLogin (staffID: String, password: String): Boolean {
        if(staffID == "admin" && password == "1234"){
            return true
        }
        return false
    }
}