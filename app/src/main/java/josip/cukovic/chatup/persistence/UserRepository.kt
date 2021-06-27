package josip.cukovic.chatup.persistence

import josip.cukovic.chatup.model.User


object UserRepository {
    var userId: String ?= null
    val users: MutableList<User> = mutableListOf()

    fun add(user: User) = users.add(user)
    fun clearAllUsers() = users.clear()
}