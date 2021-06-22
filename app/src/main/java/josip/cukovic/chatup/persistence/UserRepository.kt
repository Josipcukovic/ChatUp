package josip.cukovic.chatup.persistence

import josip.cukovic.chatup.model.User

object UserRepository {
        val users: MutableList<User>
    init {
        users = retrieveUsers()
    }
    private fun retrieveUsers(): MutableList<User> {
        return mutableListOf(
                ///testni podaci
                User("Josip", "cukone1@gmail.com","1"),
                User("ANA", "atomic17@gmail.com","2"),
                User("Josip", "cukone1@gmail.com","1"),
                User("ANA", "atomic17@gmail.com","2"),
                User("Josip", "cukone1@gmail.com","1"),
                User("ANA", "atomic17@gmail.com","2"),
                User("Josip", "cukone1@gmail.com","1"),
                User("ANA", "atomic17@gmail.com","2"),
                User("Josip", "cukone1@gmail.com","1"),
                User("ANA", "atomic17@gmail.com","2"),
                User("Josip", "cukone1@gmail.com","1"),
                User("ANA", "atomic17@gmail.com","2")
        )
    }
}