package josip.cukovic.chatup.adapters.users

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.activities.ChatActivity
import josip.cukovic.chatup.models.User

class UsersViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    fun bind(user: User){
        itemView.findViewById<TextView>(R.id.tvName).text ="User name: ${user.name}"
        itemView.findViewById<TextView>(R.id.tvEmail).text ="User email: ${user.email}"

        itemView.setOnClickListener{
            val chatIntent = Intent(context,ChatActivity::class.java)
            chatIntent.putExtra("id",user.id)
            chatIntent.putExtra("name",user.name)
            context.startActivity(chatIntent)
        }
    }
}