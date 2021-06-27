package josip.cukovic.chatup.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.SoundPool
import josip.cukovic.chatup.adapters.messages.MessagesRecyclerAdapter
import josip.cukovic.chatup.databinding.ActivityChatBinding
import josip.cukovic.chatup.data.Firebase
import josip.cukovic.chatup.data.MessageRepository
import josip.cukovic.chatup.data.UserRepository


class ChatActivity : AppCompatActivity() {
    private lateinit var chatBinding: ActivityChatBinding
    var userId: String = ""
    private val sound = SoundPool()

    companion object{
        lateinit var recyclerView: RecyclerView
       lateinit var adapter: MessagesRecyclerAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)

        val userName = intent.extras?.getString("name").toString()
        userId = intent.extras?.getString("id").toString()

        supportActionBar!!.title = userName
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

            val messageText = chatBinding.inputMessageEt.text.toString().trim()

            if(messageText.isNotEmpty()){
                chatBinding.inputMessageEt.text.clear()
                val sender = Firebase.getCurrentUserId().toString()
                val receiver = userId
                sound.playSound(R.raw.mesagesent)
                Firebase.saveMessage(messageText,sender,receiver,"false")
            }else{
                Toast.makeText(this, "You can't send empty message", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun displayData() {
        chatBinding.messageRecycler.adapter = MessagesRecyclerAdapter(MessageRepository.messages)
        val recycler = chatBinding.messageRecycler
        val adapter = recycler.adapter as MessagesRecyclerAdapter
        recyclerView = recycler
        ChatActivity.adapter = adapter

        Firebase.loadMessages()
    }

    override fun onPause() {
        super.onPause()
        Firebase.unsubscribeMessageListener()
        finish()
    }

}