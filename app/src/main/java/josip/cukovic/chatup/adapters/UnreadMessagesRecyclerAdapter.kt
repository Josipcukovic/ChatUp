package josip.cukovic.chatup.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.ChatUpApplication
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.Message

class UnreadMessagesRecyclerAdapter(message: MutableList<Message>): RecyclerView.Adapter<UnreadMessagesViewHolder>() {
    val messages: MutableList<Message> = mutableListOf()

    init{
        this.messages.addAll(message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnreadMessagesViewHolder {
        val unreadMessageView = LayoutInflater.from(parent.context).inflate(R.layout.unread_messages_display,parent,false)
        return UnreadMessagesViewHolder(unreadMessageView,parent.context)
    }

    override fun onBindViewHolder(holder: UnreadMessagesViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun dataChanged(message: MutableList<Message>){
        this.messages.clear()
        this.messages.addAll(message)
        notifyDataSetChanged()
    }
}