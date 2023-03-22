package ict2105.team02.application.ui.help
import android.speech.tts.TextToSpeech
import android.os.Bundle
import android.speech.tts.TextToSpeechService
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import ict2105.team02.application.databinding.FragmentHelpCleanBinding
import java.util.*


class EndoscopeCleaningFragment : Fragment() , TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentHelpCleanBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    private var currentVideoId : String = ""
    private lateinit var tts: TextToSpeech
    private var isPaused = false
    private var currentPosition: Int = 0
    private var allText: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val TAG = this.javaClass.simpleName
        binding = FragmentHelpCleanBinding.inflate(layoutInflater, container, false)
        tts = TextToSpeech(context, this)

        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                currentPosition = 0
            }

            override fun onDone(utteranceId: String?) {
                currentPosition = 0
            }

            override fun onError(utteranceId: String?) {
                currentPosition = 0
            }

            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                currentPosition = end
            }

            override fun onStop(utteranceId: String?, interrupted: Boolean) {
                if (interrupted) {
                    currentPosition = tts.stop()
                    isPaused = true
                } else {
                    currentPosition = allText.length
                    isPaused = true
                }
            }
        })

        binding.speak.setOnClickListener {
            readAllText()
        }
        youTubePlayerView = binding.youtubePlayerView

        // Initialize the YouTubePlayerView
        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                val defaultPlayerUiController =
                DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                youTubePlayer.loadVideo(currentVideoId, 0f)
            }
        }
        // disable iframe ui
        val options = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)

        parentFragmentManager.setFragmentResultListener("helpPage", this)
        { requestKey, bundle ->
            val videoId = bundle.getString("videoId")
            if (videoId != null) {
                Log.d(TAG, videoId)
                this.currentVideoId = videoId
                updateVideo(currentVideoId)
            }
        }

        val scrollView = binding.helpCleanScroll
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val videoViewLocation = IntArray(2)
            youTubePlayerView.getLocationOnScreen(videoViewLocation)
            val scrollViewLocation = IntArray(2)
            scrollView.getLocationOnScreen(scrollViewLocation)

            if (videoViewLocation[1] + youTubePlayerView.height < scrollViewLocation[1] ||
                videoViewLocation[1] > scrollViewLocation[1] + scrollView.height) {
                youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.pause()
                    }
                })
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        youTubePlayerView.release()
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
    }
    private fun updateVideo(videoId: String) {
        currentVideoId = videoId
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(currentVideoId, 0f)
            }
        })
    }
    override fun onPause() {
        super.onPause()
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.play()
            }
        })
    }
    private fun readAllText() {
        val stringBuilder = StringBuilder()
        if (allText != null) {
            for (i in 0 until binding.LinearCleanHelp.childCount) {
                val view = binding.LinearCleanHelp.getChildAt(i)
                if (view is TextView) {
                    stringBuilder.append(view.text).append(" ")
                }
            }
            allText = stringBuilder.toString()
        }

        if (tts != null) {
            if (!isPaused) {
                var x : Int = tts.speak(allText.substring(2), TextToSpeech.QUEUE_ADD, null, "TTSSpeak")
                Log.d("TTS", x.toString())
            } else {
                currentPosition = tts.stop()
                Log.d("TTS", currentPosition.toString())

            }
            isPaused = !isPaused
        }
    }
    private fun pauseTTS() {
        if (tts != null && !isPaused) {
            currentPosition = tts.stop()
            isPaused = true
        }
    }
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                binding.speak.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }
    // ... other code ...\
}
