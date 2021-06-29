package josip.cukovic.chatup.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import josip.cukovic.chatup.fragments.FragmentUnreadMessages
import josip.cukovic.chatup.fragments.FragmentUsers

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val fragments = arrayOf(
            FragmentUsers.newInstance(),
            FragmentUnreadMessages.newInstance()
    )
    val titles = arrayOf("Users", "Unread messages")

    override fun getCount(): Int {
       return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


}