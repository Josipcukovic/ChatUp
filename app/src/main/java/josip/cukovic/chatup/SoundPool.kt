package josip.cukovic.chatup

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class SoundPool {
    private lateinit var soundPool: SoundPool
    var soundMap: HashMap<Int, Int> = HashMap()

    fun loadSounds(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
       }
        soundMap[R.raw.mesagesent] = soundPool.load(ChatUpApplication.ApplicationContext, R.raw.mesagesent, 1)
    }

    fun playSound(selectedSound: Int) {
        val soundID = soundMap[selectedSound] ?: 0
        soundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }
}