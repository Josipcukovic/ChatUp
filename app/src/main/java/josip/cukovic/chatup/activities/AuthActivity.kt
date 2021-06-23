package josip.cukovic.chatup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import josip.cukovic.chatup.R
import josip.cukovic.chatup.adapters.FragmentAdapter
import josip.cukovic.chatup.databinding.ActivityAuthBinding
import josip.cukovic.chatup.persistence.Firebase


class AuthActivity : AppCompatActivity() {
    private lateinit var authBinding: ActivityAuthBinding
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(authBinding.root)
        setupUi()
    }

    private fun setupUi() {
        viewPager = authBinding.viewPager
        viewPager.adapter = FragmentAdapter(supportFragmentManager)
        authBinding.tabLayout.setupWithViewPager(viewPager)

        /*viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    ///refreshaj podatke koji se prikazuju
                    val adapter = findViewById<RecyclerView>(R.id.userRecycler).adapter as UsersRecyclerAdapter
                    adapter.dataAdded(UserRepository.users)
                    findViewById<RecyclerView>(R.id.userRecycler).scrollToPosition(adapter.itemCount-1)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })*/


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