package josip.cukovic.chatup.persistence

import josip.cukovic.chatup.model.User


object UserRepository {
    val users: MutableList<User> = mutableListOf()

    fun add(user: User) = users.add(user)
    fun clearThemAll() = users.clear()
}