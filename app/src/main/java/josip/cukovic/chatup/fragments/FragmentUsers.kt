package josip.cukovic.chatup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.adapters.users.UsersRecyclerAdapter
import josip.cukovic.chatup.databinding.FragmentUsersBinding
import josip.cukovic.chatup.data.Firebase
import josip.cukovic.chatup.data.UserRepository

class FragmentUsers: Fragment() {
    lateinit var userFragmentBinding : FragmentUsersBinding

    companion object {
        fun newInstance(): FragmentUsers{
            return FragmentUsers()
        }

        lateinit var adapter: UsersRecyclerAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userFragmentBinding = FragmentUsersBinding.inflate(inflater,container,false)
        userFragmentBinding.userRecycler.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        displayData()
        return userFragmentBinding.root
    }

    private fun displayData() {
        userFragmentBinding.userRecycler.adapter = UsersRecyclerAdapter(UserRepository.users)
        adapter = userFragmentBinding.userRecycler.adapter as UsersRecyclerAdapter
    }

    override fun onPause() {
        super.onPause()
        Firebase.unsubscribeUserListener()
    }

    override fun onResume() {
        super.onResume()
        Firebase.loadUsers()
    }

}