package josip.cukovic.chatup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import josip.cukovic.chatup.databinding.ActivityRegisterBinding
import josip.cukovic.chatup.persistence.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        setupUi()
    }

    private fun setupUi() {
        registerBinding.btnRegister.setOnClickListener{
            val email = registerBinding.etEmail.text.toString().trim()
            val name = registerBinding.etName.text.toString().trim()
            val surname = registerBinding.etSurname.text.toString().trim()
            val password = registerBinding.etPassword.text.toString().trim()

            if(email.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }else{
                val userName = name + " " + surname
                Firebase.createUser(email,password,userName, this)

            }
        }

    }

}