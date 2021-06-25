package josip.cukovic.chatup.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.Message

class UnreadMessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(message: Message){
        itemView.findViewById<TextView>(R.id.tvName).text = message.senderId
        itemView.findViewById<TextView>(R.id.tvUnreadMessage).text = message.textMessage
    }
}