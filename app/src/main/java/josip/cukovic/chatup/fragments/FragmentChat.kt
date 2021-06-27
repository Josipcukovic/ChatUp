package josip.cukovic.chatup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.adapters.unreadmessages.UnreadMessagesRecyclerAdapter
import josip.cukovic.chatup.databinding.FragmentChatBinding
import josip.cukovic.chatup.data.Firebase
import josip.cukovic.chatup.data.MessageRepository
import josip.cukovic.chatup.data.UserRepository

class FragmentChat: Fragment() {
    lateinit var fragmentChatBinding: FragmentChatBinding

    companion object {
        fun newInstance(): FragmentChat{
            return FragmentChat()
        }
        lateinit var adapter: UnreadMessagesRecyclerAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false)
        fragmentChatBinding.unreadMessagesRecycler.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        setupUi()
        return fragmentChatBinding.root
    }

    private fun setupUi() {
        fragmentChatBinding.unreadMessagesRecycler.adapter = UnreadMessagesRecyclerAdapter(MessageRepository.unreadMessages)
        val  recycler = fragmentChatBinding.unreadMessagesRecycler
        adapter = recycler.adapter as UnreadMessagesRecyclerAdapter

        Firebase.updateUnreadMessage()
    }

    override fun onResume() {
        super.onResume()
        UserRepository.userId = null
        adapter.dataChanged(MessageRepository.unreadMessages)
    }
}