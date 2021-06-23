package josip.cukovic.chatup.adapters


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.Message
import josip.cukovic.chatup.persistence.Firebase

class MessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message){
        itemView.findViewById<TextView>(R.id.mesageTv).text = message.textMessage
        if(Firebase.getCurrentUserId().equals(message.senderId)){
            itemView.findViewById<ImageView>(R.id.userPictureIv).visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.myPictureIv).visibility= View.VISIBLE
        }else{
            itemView.findViewById<ImageView>(R.id.userPictureIv).visibility = View.VISIBLE
            itemView.findViewById<ImageView>(R.id.myPictureIv).visibility= View.GONE
        }
    }

}