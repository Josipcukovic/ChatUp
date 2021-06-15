package josip.cukovic.chatup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import josip.cukovic.chatup.R

class FragmentChat: Fragment() {

    companion object {
        fun newInstance(): FragmentChat{
            return FragmentChat()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}