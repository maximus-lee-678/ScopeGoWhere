package ict2105.team02.application.ui.help

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import ict2105.team02.application.databinding.FragmentHelpHowToUseAppBinding
import ict2105.team02.application.utils.TAG

class HowToUseAppFragment : Fragment() {

    private lateinit var binding: FragmentHelpHowToUseAppBinding
    private lateinit var webView: WebView
    private var currentVideoId: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHelpHowToUseAppBinding.inflate(layoutInflater, container, false)

        webView = binding.WebView
        webView.isLongClickable = false
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.mediaPlaybackRequiresUserGesture = false
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: android.graphics.Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        parentFragmentManager.setFragmentResultListener("helpPage", this) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val videoId = bundle.getString("videoId")
            if (videoId != null) {
                Log.d(TAG, videoId)
                this.currentVideoId = videoId
                updateVideo(currentVideoId)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.removeAllViews()
        webView.destroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView.removeAllViews()
        webView.destroy()
    }

    private fun updateVideo(videoId: String) {
        currentVideoId = videoId
        val html = """
            <html>
                <head>
                    <title>Video Player</title>
                    <style>
                        body {
                            margin: 0;
                        }
                        iframe {
                            width: 100%;
                            height: 80%;
                        }
                         iframe::-webkit-media-controls-fullscreen-button {
                            display: none !important;
                        }
                    </style>
                </head>
                <body>
                    <iframe src="https://www.youtube.com/embed/$videoId?autoplay=1&controls=1" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"  sandbox="allow-scripts allow-same-origin" donotallowfullscreen ></iframe>
                </body>
            </html>
        """
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }
}
