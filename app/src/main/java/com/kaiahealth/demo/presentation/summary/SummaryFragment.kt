package com.kaiahealth.demo.presentation.summary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kaiahealth.demo.R
import com.kaiahealth.demo.databinding.FragmentSummaryBinding
import com.kaiahealth.demo.presentation.home.HomeUIState
import com.kaiahealth.demo.presentation.MainViewModel
import com.kaiahealth.demo.presentation.summary.adapter.SummaryItemAdapter
import com.kaiahealth.demo.utils.viewBindingDelegate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SummaryFragment : Fragment(R.layout.fragment_summary) {

    private val viewBinding by viewBindingDelegate(FragmentSummaryBinding::bind)
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var summaryAdapter: SummaryItemAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserver()
    }

    private fun initViews() {
        summaryAdapter = SummaryItemAdapter()
        viewBinding.summaryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = summaryAdapter
        }

        viewBinding.finishButton.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment,false)
        }
    }

    private fun initObserver() {
        mainViewModel.homeExercisesLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                is HomeUIState.Success -> {
                    val exerciseList = status.exercises
                    val doneExerciseList = mainViewModel.doneExercises
                    summaryAdapter?.updateItemList(exerciseList, doneExerciseList)
                    viewBinding.summaryMessage.text =
                        getString(R.string.summary_message, doneExerciseList.size)
                }
                else -> {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        val contextView = viewBinding.summaryRecyclerView
        Snackbar.make(contextView, R.string.error, Snackbar.LENGTH_SHORT).show()
    }
}