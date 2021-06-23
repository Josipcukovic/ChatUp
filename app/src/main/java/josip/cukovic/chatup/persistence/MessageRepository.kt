package josip.cukovic.chatup.persistence

import josip.cukovic.chatup.model.Message


object MessageRepository {
    val messages: MutableList<Message> = mutableListOf()

    fun add(message: Message) = messages.add(message)
    fun removeAllMessages() = messages.clear()
}