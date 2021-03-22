package com.example.betterfit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_bottom.*


class BottomFragment : BottomSheetDialogFragment() {

    lateinit var videoId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.BottomSheetDialogTheme);



    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        videoId = arguments?.getString("id").toString()

        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }
    override fun onPause() {
        (activity as ExerciseActivity?)?.setupRestView()

        super.onPause()
    }

    override fun onStart() {
        lifecycle.addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                Log.d("TESTYOUTUBE",videoId)
                youTubePlayer.loadVideo(videoId, 0f)

            }
        })
        super.onStart()
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnClose.setOnClickListener {
           dismiss()
        }
        super.onViewCreated(view, savedInstanceState)
    }


}