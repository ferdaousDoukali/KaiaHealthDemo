package com.kaiahealth.demo.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kaiahealth.demo.R
import com.kaiahealth.demo.core.model.Exercise
import com.kaiahealth.demo.databinding.FragmentHomeBinding
import com.kaiahealth.demo.presentation.MainViewModel
import com.kaiahealth.demo.presentation.home.adapter.HomeItemAdapter
import com.kaiahealth.demo.utils.viewBindingDelegate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewBinding by viewBindingDelegate(FragmentHomeBinding::bind)
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private var adapterHome: HomeItemAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserver()
    }

    private fun initViews() {
        adapterHome = HomeItemAdapter {
            onItemSelected(it)
        }
        viewBinding.homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterHome
        }

        viewBinding.homeSwipeRefreshLayout.setOnRefreshListener {
            mainViewModel.loadAllExercises()
        }

        viewBinding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_exerciseFragment)
        }
    }

    private fun onItemSelected(item: Exercise) {
        mainViewModel.onItemSelected(item)
    }

    private fun initObserver() {
        mainViewModel.homeExercisesLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                is HomeUIState.Error -> {
                    viewBinding.homeSwipeRefreshLayout.isRefreshing = false
                    showError()
                }
                HomeUIState.Loading -> {
                    viewBinding.homeSwipeRefreshLayout.isRefreshing = true
                }
                is HomeUIState.Success -> {
                    viewBinding.homeSwipeRefreshLayout.isRefreshing = false
                    val favoriteItems = mainViewModel.favoriteExercises
                    adapterHome?.updateItemList(status.exercises, favoriteItems)
                }
            }
        }
    }

    override fun onPause() {
        mainViewModel.saveFavoriteExercises()
        super.onPause()
    }

    private fun showError() {
        val contextView = viewBinding.homeRecyclerView
        Snackbar.make(contextView, R.string.error, Snackbar.LENGTH_SHORT).show()
    }
}