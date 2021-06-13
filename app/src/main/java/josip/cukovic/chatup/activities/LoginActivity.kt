package josip.cukovic.chatup.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import josip.cukovic.chatup.databinding.ActivityLoginBinding
import josip.cukovic.chatup.persistence.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        setupUi()
    }

    private fun setupUi() {
        loginBinding.btnLogin.setOnClickListener{

            val email = loginBinding.etEmail.text.toString().trim()
            val password = loginBinding.etPassword.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty()){
                Firebase.loginUser(email,password,applicationContext)
            }else{
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }

        }
    }

}