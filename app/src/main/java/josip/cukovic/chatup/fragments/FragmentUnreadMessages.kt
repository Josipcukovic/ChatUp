package josip.cukovic.chatup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.adapters.unreadmessages.UnreadMessagesRecyclerAdapter
import josip.cukovic.chatup.data.Firebase
import josip.cukovic.chatup.data.MessageRepository
import josip.cukovic.chatup.data.UserRepository
import josip.cukovic.chatup.databinding.FragmentUnreadMessagesBinding

class FragmentUnreadMessages: Fragment() {
    lateinit var fragmentChatBinding: FragmentUnreadMessagesBinding

    companion object {
        fun newInstance(): FragmentUnreadMessages{
            return FragmentUnreadMessages()
        }
        lateinit var adapter: UnreadMessagesRecyclerAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentChatBinding = FragmentUnreadMessagesBinding.inflate(inflater,container,false)
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