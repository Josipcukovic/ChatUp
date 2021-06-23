package josip.cukovic.chatup.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.Message

class MessagesRecyclerAdapter(messages : MutableList<Message>): RecyclerView.Adapter<MessagesViewHolder>() {
    private val messages: MutableList<Message> = mutableListOf()

    init {
        this.messages.addAll(messages)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val messagesView = LayoutInflater.from(parent.context).inflate(R.layout.message_display,parent,false)
        return MessagesViewHolder(messagesView)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

}