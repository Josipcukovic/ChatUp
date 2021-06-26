package josip.cukovic.chatup.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import josip.cukovic.chatup.ChatUpApplication
import josip.cukovic.chatup.R
import josip.cukovic.chatup.adapters.FragmentAdapter
import josip.cukovic.chatup.adapters.UnreadMessagesRecyclerAdapter
import josip.cukovic.chatup.databinding.ActivityAuthBinding
import josip.cukovic.chatup.persistence.Firebase
import josip.cukovic.chatup.persistence.MessageRepository
import josip.cukovic.chatup.persistence.UserRepository


class AuthActivity : AppCompatActivity() {
    private lateinit var authBinding: ActivityAuthBinding
    private lateinit var viewPager: ViewPager
    private var adapter = FragmentAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(authBinding.root)
        setupUi()
    }


    private fun setupUi() {
        viewPager = authBinding.viewPager
        adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        authBinding.tabLayout.setupWithViewPager(viewPager)


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {

                if (position == 1) {
                    val recycler = findViewById<RecyclerView>(R.id.unreadMessagesRecycler)
                   val adapter = recycler.adapter as UnreadMessagesRecyclerAdapter
                    adapter.dataChanged(MessageRepository.unreadMessages)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onStop() {
        super.onStop()
        UserRepository.clearThemAll()
    }

///menu opcije
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_options,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if(item.itemId == R.id.LogoutOption){
            Firebase.logOut()
            val logoutIntent = Intent(this,MainActivity::class.java)
            startActivity(logoutIntent)
            Toast.makeText(this, "You have successfully logged out", Toast.LENGTH_SHORT).show()
            finish()
        }else if(item.itemId == R.id.ProfileOption){
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
        }
        return true
    }

}