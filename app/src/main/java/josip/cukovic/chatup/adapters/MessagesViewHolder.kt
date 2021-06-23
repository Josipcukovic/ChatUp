package josip.cukovic.chatup.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.Message

class MessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(message: Message){
        itemView.findViewById<TextView>(R.id.mesageTv).text = message.textMessage
    }
}