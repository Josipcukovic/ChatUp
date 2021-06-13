package josip.cukovic.chatup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import josip.cukovic.chatup.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding
    private lateinit var authFirebase : FirebaseAuth
    private lateinit var db : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        authFirebase = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        loginBinding.btnLogin.setOnClickListener{
            val email = loginBinding.etEmail.text.toString().trim()
            val password = loginBinding.etPassword.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty()){
                loginUser(email,password)
            }else{
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun loginUser(email: String, password: String) {
            authFirebase.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    task: Task<AuthResult> ->
                    if (task.isSuccessful){
                        val intent = Intent(this,AuthActivity::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()

                    }
                }
    }
}