package com.kaiahealth.demo.presentation.exercise

import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kaiahealth.demo.R
import com.kaiahealth.demo.databinding.FragmentExerciseBinding
import com.kaiahealth.demo.presentation.MainViewModel
import com.kaiahealth.demo.utils.viewBindingDelegate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExerciseFragment : Fragment(R.layout.fragment_exercise) {

    private val viewBinding by viewBindingDelegate(FragmentExerciseBinding::bind)
    private val mainViewModel by sharedViewModel<MainViewModel>()

    private var mediaController: MediaController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mediaController = MediaController(requireContext())
        mediaController?.setAnchorView(viewBinding.videoView)

        viewBinding.run {
            closeButton.setOnClickListener {
                findNavController().popBackStack()
            }

            skipButton.setOnClickListener {
                skipExercise()
            }

            videoView.apply {
                setMediaController(mediaController)
                //start playing first video
                playExerciseVideo()

                setOnCompletionListener {
                    onExerciseCompleted()
                    moveToNextExercise()
                }

                setOnPreparedListener {
                    hideProgressBar()
                    start()
                }
            }
        }
    }

    private fun playExerciseVideo() {
        showProgressBar()
        val exercise = mainViewModel.getCurrentExercise()
        if (exercise == null) {
            //no more exercises to play -> move to summary screen
            findNavController().navigate(R.id.action_exerciseFragment_to_summaryFragment)
        } else {
            viewBinding.title.apply {
                text = exercise.name
                postDelayed({ visibility = View.INVISIBLE }, TIME_TO_HIDE_TITLE)
            }
            viewBinding.videoView.setVideoPath(exercise.videoUrl)
        }
    }


    private fun showProgressBar() {
        viewBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        viewBinding.progressBar.visibility = View.GONE
    }

    private fun onExerciseCompleted(){
        mainViewModel.onExerciseCompleted()
    }

    private fun moveToNextExercise(){
        mainViewModel.moveToNextExercise()
        playExerciseVideo()
    }

    private fun skipExercise(){
        viewBinding.videoView.stopPlayback()
        mainViewModel.skipExercise()
        playExerciseVideo()
    }

    companion object {
        private const val TIME_TO_HIDE_TITLE = 5000L
    }
}