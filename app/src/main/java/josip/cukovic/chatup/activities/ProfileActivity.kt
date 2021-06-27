package josip.cukovic.chatup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import josip.cukovic.chatup.databinding.ActivityProfileBinding
import josip.cukovic.chatup.data.Firebase

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