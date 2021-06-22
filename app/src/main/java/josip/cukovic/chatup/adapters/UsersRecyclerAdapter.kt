package josip.cukovic.chatup.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import josip.cukovic.chatup.R
import josip.cukovic.chatup.model.User

class UsersRecyclerAdapter(users: MutableList<User>): RecyclerView.Adapter<UsersViewHolder>() {
    private val users: MutableList<User> = mutableListOf()

    init {
        this.users.addAll(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val userView = LayoutInflater.from(parent.context).inflate(R.layout.user_display,parent,false)
        return UsersViewHolder(userView,parent.context)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun dataAdded(user: MutableList<User>){
        val number = itemCount
        this.users.clear()
        this.users.addAll((user))
        if(itemCount == number) return
        notifyItemInserted(itemCount - 1)
    }
}