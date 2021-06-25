package josip.cukovic.chatup.persistence

import josip.cukovic.chatup.model.Message


object MessageRepository {
    val messages: MutableList<Message> = mutableListOf()

    val unreadMessages: MutableList<Message> = mutableListOf(
            ///testni podaci
            Message("poruka","sender", "primatelj", "true"),
            Message("poruka","sender", "primatelj", "true"),
            Message("poruka","sender", "primatelj", "true"),
            Message("poruka","sender", "primatelj", "true"),
            Message("poruka2","sender2", "primatelj", "true"),
            Message("poruka3","sender3", "primatelj", "true"),
            Message("poruka4","sender4", "primatelj", "true"),
            Message("poruka5","sender5", "primatelj", "true")
    )
    var unread = 0

    fun addUnreadMessage(message: Message) = unreadMessages.add(message)
    fun removeAllUnreadMessages() = unreadMessages.clear()

    fun add(message: Message) = messages.add(message)
    fun removeAllMessages() = messages.clear()
}