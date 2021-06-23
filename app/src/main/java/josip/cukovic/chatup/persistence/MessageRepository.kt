package josip.cukovic.chatup.persistence

import josip.cukovic.chatup.model.Message

object MessageRepository {
    val messages: MutableList<Message>
    init {
        messages = retrieveMessages()
    }
    private fun retrieveMessages(): MutableList<Message> {
        return mutableListOf(
            Message("poruka1","randomid", "kk"),
            Message("poruka2","randomid", "kk"),
            Message("poruka1","randomid", "kk"),
            Message("poruka2","randomid", "kk"),
            Message("poruka1","randomid", "kk"),
            Message("poruka2","randomid", "kk"),
            Message("poruka1","randomid", "kk"),
            Message("poruka2","randomid", "kk")

        )
    }
}