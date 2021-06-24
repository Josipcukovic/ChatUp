package josip.cukovic.chatup.adapters


import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.ChatUpApplication
import josip.cukovic.chatup.R
import josip.cukovic.chatup.activities.ChatActivity
import josip.cukovic.chatup.model.Message
import josip.cukovic.chatup.persistence.Firebase
import josip.cukovic.chatup.persistence.UserRepository

class MessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message){

        itemView.findViewById<TextView>(R.id.mesageTv).text = message.textMessage

            if(Firebase.getCurrentUserId().equals(message.senderId)){
                itemView.setBackgroundColor(Color.rgb(200 , 200, 200))
                itemView.findViewById<ImageView>(R.id.userPictureIv).visibility = View.GONE
                itemView.findViewById<ImageView>(R.id.myPictureIv).visibility= View.VISIBLE
            }else{
                itemView.setBackgroundColor(Color.rgb(224, 224, 224))
                itemView.findViewById<ImageView>(R.id.userPictureIv).visibility = View.VISIBLE
                itemView.findViewById<ImageView>(R.id.myPictureIv).visibility= View.GONE
            }

    }

}