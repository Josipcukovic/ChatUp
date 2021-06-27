package josip.cukovic.chatup.data


import josip.cukovic.chatup.models.Message


object MessageRepository {
    val messages: MutableList<Message> = mutableListOf()
    val unreadMessages: MutableList<Message> = mutableListOf()

    fun addUnreadMessage(message: Message) = unreadMessages.add(message)
    fun removeAllUnreadMessages() = unreadMessages.clear()


    fun addMessage(message: Message) = messages.add(message)
    fun removeAllMessages() = messages.clear()
}