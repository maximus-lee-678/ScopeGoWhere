package ict2105.team02.application.ui.help
import android.graphics.Typeface
import android.speech.tts.TextToSpeech
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentHelpPageBinding
import java.util.*


class HelpPageFragment : Fragment() , TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentHelpPageBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    private var currentVideoId : String = ""
    private lateinit var tts: TextToSpeech
    private var isPaused = false
	var instructions: MutableList<String> = mutableListOf()
    private var allText: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val TAG = this.javaClass.simpleName
        binding = FragmentHelpPageBinding.inflate(layoutInflater, container, false)
        tts = TextToSpeech(context, this)

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
	        if (videoId != null) {
		        val stringArrayID = bundle.getInt("stringArrayID")
		        instructions = resources.getStringArray(stringArrayID).toMutableList()
		        setUpInstruction()
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
	private fun setUpInstruction() {
		val linearLayout: LinearLayout = binding.LinearCleanHelp
		for ((index, instruction) in instructions.withIndex()) {
			if (index == 0) {
				binding.HelpPageTitle.text =  instruction
				continue
			}

			val textView = TextView(context)
			textView.layoutParams = LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
			)
			textView.text = Html.fromHtml(instruction)
			textView.textSize = 16f
			textView.setPadding(
				dpToPx(16), dpToPx(16), dpToPx(16),
				if (index == instructions.size - 1) dpToPx(16) else 0
			)
			textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_700))

			// Apply bold styling to the text before the colon
			val builder = SpannableStringBuilder(textView.text)
			val colonIndex = instruction.indexOf(":")
			if (colonIndex >= 0) {
				builder.setSpan(
					StyleSpan(Typeface.BOLD),
					0, colonIndex,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
				)
			}
			textView.text = builder

			linearLayout.addView(textView)
		}
	}

	fun dpToPx(dp: Int): Int {
		val scale = resources.displayMetrics.density
		return (dp * scale + 0.5f).toInt()
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
		        if (view is TextView &&  view !is Button) {
			        tts.speak(view.text.toString(), TextToSpeech.QUEUE_ADD, null, "TTSSpeak")
			        tts.playSilentUtterance(1000, TextToSpeech.QUEUE_ADD,"TTSSpeak" )
			        stringBuilder.append(view.text.toString())
		        }
	        }
            allText = stringBuilder.toString()
        }

        if (tts != null) {
            if (!isPaused) {

            } else {
				tts.stop()

            }
            isPaused = !isPaused
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.CANADA)
	        tts.setSpeechRate(1.5f)
	        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                binding.speak.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }
    // ... other code ...\
}
