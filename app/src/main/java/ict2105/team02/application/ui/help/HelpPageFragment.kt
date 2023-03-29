package ict2105.team02.application.ui.help

import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
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


class HelpPageFragment : Fragment() {
    private lateinit var binding: FragmentHelpPageBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    private var currentVideoId: String = ""
    private lateinit var tts: AudioNarrator
    var instructions: MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup binding
        binding = FragmentHelpPageBinding.inflate(inflater, container, false)
        youTubePlayerView = binding.youtubePlayerView
        //Setup TTS
        tts = context?.let { AudioNarrator(it) }!!
        // Initialize the YouTubePlayerView
        initializeYoutube()
        // Setup Information from context
        setUpHelpPage()
        // Setup pause video Scroll Function
        setupScrollObserver(binding.helpCleanScroll)
        // Setup TextToSpeech
        binding.speak.setOnClickListener {
            val isPlaying = tts.toggleTextToSpeech { readAllText(binding.LinearCleanHelp) }
            if (!isPlaying)
                binding.speak.text = resources.getString(R.string.tts_play)
            else
                binding.speak.text = resources.getString(R.string.tts_stop)
            pauseYoutubeVideo()
        }
        return binding.root
    }

    private fun initializeYoutube() {
        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                val defaultPlayerUiController =
                    DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                youTubePlayer.loadVideo(currentVideoId, 0f)
                youTubePlayer.play()
            }
        }
        // disable iframe ui
        val options = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
    }

    private fun setUpHelpPage() {
        parentFragmentManager.setFragmentResultListener("helpPage", this)
        { requestKey, bundle ->
            val videoId = bundle?.getString("videoId")
            val stringArrayID = bundle?.getInt("stringArrayID")
            if (videoId != null) {
                this.currentVideoId = videoId
                updateVideo(currentVideoId)
            }
            if (stringArrayID != null) {
                instructions = resources.getStringArray(stringArrayID).toMutableList()
                setUpInstruction()
            }
        }
    }

    private fun setupScrollObserver(scrollView: ScrollView) {
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val videoViewLocation = IntArray(2)
            youTubePlayerView.getLocationOnScreen(videoViewLocation)
            val scrollViewLocation = IntArray(2)
            scrollView.getLocationOnScreen(scrollViewLocation)

            if (videoViewLocation[1] + youTubePlayerView.height < scrollViewLocation[1] ||
                videoViewLocation[1] > scrollViewLocation[1] + scrollView.height
            ) {
                pauseYoutubeVideo()
            }
        }
    }

    private fun setUpInstruction() {
        val linearLayout: LinearLayout = binding.LinearCleanHelp
        for ((index, instruction) in instructions.withIndex()) {
            if (index == 0) {
                binding.HelpPageTitle.text = instruction
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

    private fun pauseYoutubeVideo() {
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()
            }
        })
    }

    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
        tts.closeTTS()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        youTubePlayerView.release()
        tts.closeTTS()

    }

    private fun updateVideo(videoId: String) {
        currentVideoId = videoId
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(currentVideoId, 0f)
            }
        })
    }

    private fun readAllText(linearLayout: LinearLayout) {
        for (i in 0 until linearLayout.childCount) {
            val view = linearLayout.getChildAt(i)
            if (view is TextView && view !is Button) {
                tts.addToQueue(view.text.toString())
            }
        }

    }

    override fun onPause() {
        super.onPause()
        pauseYoutubeVideo()
        tts.stopTTS()
        binding.speak.text = resources.getString(R.string.tts_play)
    }

    override fun onResume() {
        super.onResume()
        pauseYoutubeVideo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInstruction()
    }
}
