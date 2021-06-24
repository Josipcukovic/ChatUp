package josip.cukovic.chatup

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class SoundPool {
    private lateinit var mSoundPool: SoundPool
    var mSoundMap: HashMap<Int, Int> = HashMap()

    fun loadSounds(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            mSoundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
       }
        mSoundMap[R.raw.mesagesent] = mSoundPool.load(ChatUpApplication.ApplicationContext, R.raw.mesagesent, 1)
    }

    fun playSound(selectedSound: Int) {
        val soundID = mSoundMap[selectedSound] ?: 0
        mSoundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }
}