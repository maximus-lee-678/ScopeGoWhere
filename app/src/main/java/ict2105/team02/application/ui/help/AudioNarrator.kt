package ict2105.team02.application.ui.help

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class AudioNarrator(context: Context) : TextToSpeech.OnInitListener {
    // Callback for when an item is clicked
    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isPaused = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts.setLanguage(Locale.CANADA)
            tts.setSpeechRate(1.5f)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    fun closeTTS() {
        stopTTS()
        tts.shutdown()

    }

    fun stopTTS() {
        tts.stop()
        isPaused = false
    }

    fun addToQueue(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "TTSSpeak")
        tts.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD, "TTSSpeak")
    }

    fun toggleTextToSpeech(callbackToAddQueue: (() -> Unit)): Boolean {
        if (!isPaused) {
            callbackToAddQueue()
        } else {
            tts.stop()
        }
        isPaused = !isPaused

        return isPaused
    }
}