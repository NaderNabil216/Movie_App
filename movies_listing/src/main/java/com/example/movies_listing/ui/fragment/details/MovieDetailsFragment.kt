package com.example.movies_listing.ui.fragment.details

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.movies_listing.databinding.FragmentMovieDetailsBinding
import com.youxel.core.base.fragment.BaseFragment
import com.youxel.core.base.view_model.ApiState
import com.youxel.core.domain.entities.base.ErrorStatus
import com.youxel.core.utils.watchYoutube
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding,MovieDetailsViewModel,MovieDetailsUiHelper>(FragmentMovieDetailsBinding::inflate) {
    override val viewModel: MovieDetailsViewModel by viewModels()
    @Inject
    override lateinit var fragmentHelper: MovieDetailsUiHelper

    private val args:MovieDetailsFragmentArgs by navArgs()
    private val trailerAdapter = MovieDetailsTrailerAdapter(::onTrailerItemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarListener?.run {
            showActivityToolbar(true)
            setActivityToolbarTitle(getString(com.youxel.core.R.string.details),Gravity.CENTER)

        }
        getMovieDetails()
        collectMovieDetailsData()
    }

    private fun getMovieDetails(){
        viewModel.send(MovieDetailsIntent.GetMovieDetails(args.movieId))
    }

    private fun collectMovieDetailsData() {
        lifecycleScope.launchWhenStarted {
            viewModel.movieDetailsMutableStateFlow.asLiveData().observe(viewLifecycleOwner){
                when(it){
                    is ApiState.Success ->{
                        fragmentHelper.setData(binding,trailerAdapter,it.successData)
                    }
                }
            }
        }
    }

    private fun onTrailerItemClicked(trailerKey:String){
        requireContext().watchYoutube(trailerKey)
    }

    override fun onInternetStatusChanged(isConnected: Boolean) {
        if (isConnected){
            getMovieDetails()
        }else{
            baseNetworkingDialog.showDialog(
                requireActivity(),
                ErrorStatus.NO_CONNECTION
            )
        }
    }
}