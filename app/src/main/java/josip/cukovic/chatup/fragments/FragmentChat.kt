package josip.cukovic.chatup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.adapters.UnreadMessagesRecyclerAdapter
import josip.cukovic.chatup.databinding.FragmentChatBinding
import josip.cukovic.chatup.persistence.Firebase
import josip.cukovic.chatup.persistence.MessageRepository
import josip.cukovic.chatup.persistence.UserRepository

class FragmentChat: Fragment() {
    lateinit var fragmentChatBinding: FragmentChatBinding

    companion object {
        fun newInstance(): FragmentChat{
            return FragmentChat()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false)
        fragmentChatBinding.unreadMessagesRecycler.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)


        setupUi()

        return fragmentChatBinding.root
    }

    private fun getAdapter(): UnreadMessagesRecyclerAdapter{
        val  recycler = fragmentChatBinding.unreadMessagesRecycler
        val  adapter = recycler.adapter as UnreadMessagesRecyclerAdapter
        return adapter
    }

    private fun setupUi() {
        fragmentChatBinding.unreadMessagesRecycler.adapter = UnreadMessagesRecyclerAdapter(MessageRepository.unreadMessages)

        val adapter = getAdapter()
        Firebase.updateUnreadMessage(adapter)
    }


    /*override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){

        }
    }*/

    override fun onResume() {
        super.onResume()
        val adapter = getAdapter()
        UserRepository.userId = null
        adapter.dataChanged(MessageRepository.unreadMessages)
    }
}