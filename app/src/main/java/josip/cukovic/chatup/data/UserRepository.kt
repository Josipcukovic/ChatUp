package josip.cukovic.chatup.data

import josip.cukovic.chatup.models.User


object UserRepository {
    var userId: String ?= null
    val users: MutableList<User> = mutableListOf()

    fun add(user: User) = users.add(user)
    fun clearAllUsers() = users.clear()
}