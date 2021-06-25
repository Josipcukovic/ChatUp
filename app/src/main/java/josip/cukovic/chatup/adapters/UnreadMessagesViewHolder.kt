package josip.cukovic.chatup.adapters

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import josip.cukovic.chatup.R
import josip.cukovic.chatup.activities.ChatActivity
import josip.cukovic.chatup.model.Message

class UnreadMessagesViewHolder(itemView: View,private val context: Context): RecyclerView.ViewHolder(itemView) {
    private var db = FirebaseDatabase.getInstance()
    private val usersDbRef = db.getReference("Users")

    fun bind(message: Message){
        usersDbRef.child(message.senderId).child("name").get().addOnSuccessListener {
            val ime = it.value.toString()
            itemView.findViewById<TextView>(R.id.tvName).text = ime
            itemView.findViewById<TextView>(R.id.tvUnreadMessage).text = message.textMessage
        }

        itemView.setOnClickListener{
            val chatIntent = Intent(context, ChatActivity::class.java)
            chatIntent.putExtra("id",message.senderId)
            context.startActivity(chatIntent)

        }
    }
}