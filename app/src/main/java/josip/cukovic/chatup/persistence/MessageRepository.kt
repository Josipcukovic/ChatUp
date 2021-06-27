package josip.cukovic.chatup.persistence


import josip.cukovic.chatup.model.Message


object MessageRepository {
    val messages: MutableList<Message> = mutableListOf()
    val unreadMessages: MutableList<Message> = mutableListOf()

    fun addUnreadMessage(message: Message) = unreadMessages.add(message)
    fun removeAllUnreadMessages() = unreadMessages.clear()


    fun addMessage(message: Message) = messages.add(message)
    fun removeAllMessages() = messages.clear()
}