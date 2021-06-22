package josip.cukovic.chatup.adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.User
import josip.cukovic.chatup.persistence.Firebase

class UsersViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    fun bind(user: User){
        itemView.findViewById<TextView>(R.id.tvName).text ="User name:" + user.name
        itemView.findViewById<TextView>(R.id.tvEmail).text ="User email:" + user.email

        itemView.setOnClickListener{
            //pokusaj kasnije izbaciti
            Toast.makeText(context, user.name, Toast.LENGTH_SHORT).show()
        }
    }
}