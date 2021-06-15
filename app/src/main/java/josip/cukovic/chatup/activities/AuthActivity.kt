package josip.cukovic.chatup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import josip.cukovic.chatup.R
import josip.cukovic.chatup.adapters.FragmentAdapter
import josip.cukovic.chatup.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var authBinding: ActivityAuthBinding
    private lateinit var authFirebase : FirebaseAuth
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(authBinding.root)
        authFirebase = FirebaseAuth.getInstance()
        setupUi()
    }

    private fun setupUi() {
        viewPager = authBinding.viewPager

        viewPager.adapter = FragmentAdapter(supportFragmentManager)
        authBinding.tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    val welcome = "welcome" + " " + authFirebase.currentUser!!.displayName
                     findViewById<TextView>(R.id.tvWelcome).text = welcome
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }
}