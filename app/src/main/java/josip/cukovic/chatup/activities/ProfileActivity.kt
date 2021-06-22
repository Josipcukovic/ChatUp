package josip.cukovic.chatup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import josip.cukovic.chatup.databinding.ActivityProfileBinding
import josip.cukovic.chatup.persistence.Firebase

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
        setupUi()
    }

    private fun setupUi() {
        profileBinding.userNameTv.text = Firebase.getCurrentUserName()
        profileBinding.userEmailTv.text = Firebase.getCurrentUserEmail()
    }
}