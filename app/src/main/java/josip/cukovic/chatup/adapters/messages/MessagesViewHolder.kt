package josip.cukovic.chatup.adapters.messages


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.models.Message
import josip.cukovic.chatup.data.Firebase

class MessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message){
        val msgMy = itemView.findViewById<TextView>(R.id.messageMyTv)
        val msgHis = itemView.findViewById<TextView>(R.id.messageTv)

            if(Firebase.getCurrentUserId() == message.senderId){
                msgMy.text = message.textMessage
                msgHis.visibility = View.GONE
                msgMy.visibility= View.VISIBLE

            }else{
                msgHis.text = message.textMessage
                msgHis.visibility = View.VISIBLE
                msgMy.visibility= View.GONE
            }
    }
}