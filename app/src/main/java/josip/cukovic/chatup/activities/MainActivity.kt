package josip.cukovic.chatup.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import josip.cukovic.chatup.databinding.ActivityMainBinding
import josip.cukovic.chatup.manager.PreferenceManager
import josip.cukovic.chatup.data.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {

        val email = PreferenceManager().retrieveEmail()
        val password = PreferenceManager().retrievePassword()

        if((email != "default") && (password != "default") ){
            Firebase.loginUser(email,password)
        }else{
            binding.loginBtn.isEnabled = true
            binding.registerBtn.isEnabled = true
        }


        binding.loginBtn.setOnClickListener{
            val loginIntent = Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)
        }

        binding.registerBtn.setOnClickListener{
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}

