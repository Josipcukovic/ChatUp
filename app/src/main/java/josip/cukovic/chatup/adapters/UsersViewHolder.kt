package josip.cukovic.chatup.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.User

class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(user: User){
        itemView.findViewById<TextView>(R.id.tvName).text ="User name:" + user.name
        itemView.findViewById<TextView>(R.id.tvEmail).text ="User email:" + user.email
    }
}