package josip.cukovic.chatup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.adapters.MessagesRecyclerAdapter
import josip.cukovic.chatup.databinding.ActivityChatBinding
import josip.cukovic.chatup.persistence.Firebase
import josip.cukovic.chatup.persistence.MessageRepository

class ChatActivity : AppCompatActivity() {
    private lateinit var chatBinding: ActivityChatBinding
    var userId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)

        userId = intent.extras?.getString("id").toString()
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()

        setupUi()
    }

    private fun setupUi() {
        chatBinding.messageRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        displayData()
        chatBinding.sendMsgBtn.setOnClickListener{

            val poruka = chatBinding.inputMessageEt.text.toString().trim()
            if(poruka.isNotEmpty()){

                chatBinding.inputMessageEt.text.clear()
                val sender = Firebase.getCurrentUserId().toString()
                val receiver = userId
                Firebase.saveMessage(poruka,sender,receiver,this)
            }else{
                Toast.makeText(this, "You can't send empty message", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun displayData() {
        chatBinding.messageRecycler.adapter = MessagesRecyclerAdapter(MessageRepository.messages)
    }
}