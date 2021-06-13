package josip.cukovic.chatup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import josip.cukovic.chatup.R
import josip.cukovic.chatup.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var authBinding: ActivityAuthBinding
    private lateinit var authFirebase : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(authBinding.root)
        authFirebase = FirebaseAuth.getInstance()
        val welcome = "welcome" + " " + authFirebase.currentUser!!.email
        authBinding.tvWelcome.text = welcome

    }
}