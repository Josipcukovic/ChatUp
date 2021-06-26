package josip.cukovic.chatup.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.SoundPool
import josip.cukovic.chatup.adapters.MessagesRecyclerAdapter
import josip.cukovic.chatup.databinding.ActivityChatBinding
import josip.cukovic.chatup.persistence.Firebase
import josip.cukovic.chatup.persistence.MessageRepository
import josip.cukovic.chatup.persistence.UserRepository


class ChatActivity : AppCompatActivity() {
    private lateinit var chatBinding: ActivityChatBinding
    var userId: String = ""
    private val sound = SoundPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)

        val userName = intent.extras?.getString("name").toString()
        supportActionBar!!.title = userName

        userId = intent.extras?.getString("id").toString()
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()

        UserRepository.userId = userId
        setupUi()
        sound.loadSounds()
    }

    private fun setupUi() {
        val layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        layoutManager.stackFromEnd = true
        chatBinding.messageRecycler.layoutManager = layoutManager

        displayData()
        chatBinding.sendMsgBtn.setOnClickListener{

            val poruka = chatBinding.inputMessageEt.text.toString().trim()

            if(poruka.isNotEmpty()){
                chatBinding.inputMessageEt.text.clear()
                val sender = Firebase.getCurrentUserId().toString()
                val receiver = userId
                sound.playSound(R.raw.mesagesent)
                Firebase.saveMessage(poruka,sender,receiver,"false")
            }else{
                Toast.makeText(this, "You can't send empty message", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun displayData() {
        chatBinding.messageRecycler.adapter = MessagesRecyclerAdapter(MessageRepository.messages)
        val recycler = chatBinding.messageRecycler
        val adapter = recycler.adapter as MessagesRecyclerAdapter

        Firebase.loadMessages(adapter,recycler)
    }

    override fun onPause() {
        super.onPause()
        Firebase.unsubscribeMessageListener()
        finish()
    }

}