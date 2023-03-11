package ict2105.team02.application.ui.help

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import ict2105.team02.application.databinding.FragmentYoutubeBinding

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentYoutubeBinding
    private lateinit var youtubePlayerView : YouTubePlayerView
    val TAG = this::class.simpleName!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentYoutubeBinding.inflate(inflater)
        youtubePlayerView = binding.youtubePlayerView
        youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo("dQw4w9WgXcQ", 0f)
                youTubePlayer.play()

            }
        })
        youtubePlayerView.enterFullScreen();
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayerView.release()
    }


}