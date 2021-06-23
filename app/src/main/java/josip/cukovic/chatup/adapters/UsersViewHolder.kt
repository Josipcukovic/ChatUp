package josip.cukovic.chatup.adapters

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.activities.ChatActivity
import josip.cukovic.chatup.model.User
import josip.cukovic.chatup.persistence.Firebase

class UsersViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    fun bind(user: User){
        itemView.findViewById<TextView>(R.id.tvName).text ="User name:" + user.name
        itemView.findViewById<TextView>(R.id.tvEmail).text ="User email:" + user.email

        itemView.setOnClickListener{
            //pokusaj kasnije izbaciti
            val chatIntent = Intent(context,ChatActivity::class.java)
            chatIntent.putExtra("id",user.id)
            context.startActivity(chatIntent)
            //Toast.makeText(context, user.id, Toast.LENGTH_SHORT).show()
        }
    }
}