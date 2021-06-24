package josip.cukovic.chatup

import android.app.Application
import android.content.Context

class ChatUpApplication: Application() {
    companion object {
        lateinit var ApplicationContext: Context
            private set
    }
    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }
}