package com.youxel.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.youxel.core.R
import com.youxel.core.base.activity.BottomNavListener
import com.youxel.core.base.activity.ToolbarListener
import com.youxel.core.base.view_model.BaseViewModel
import com.youxel.core.base.view_model.UiState
import com.youxel.core.data.local.StorageManager
import com.youxel.core.domain.entities.base.ErrorModel
import com.youxel.core.domain.entities.base.ErrorStatus
import com.youxel.core.domain.entities.enums.ToastType
import com.youxel.core.utils.LoadingListener

import com.youxel.core.utils.hideKeyBoardOutSideTap
import com.youxel.core.utils.showToast
import com.youxel.core.utils.updateStatusBarColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ensureActive
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

@ExperimentalCoroutinesApi
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel, HelperClass : BaseUiHelper>(
    private val inflate: Inflate<VB>
) : NetworkBaseFragment() {

    //added line to view binding
    private var _binding: VB? = null
    val binding get() = _binding!!

    private val TAG = BaseFragment::class.java.simpleName

    private var mLoader: LoadingListener? = null

    abstract val viewModel: VM

    abstract val fragmentHelper: HelperClass

    protected var toolbarListener: ToolbarListener? = null

    private var bottomNavListener: BottomNavListener? = null

    @Inject
    lateinit var storageManager: StorageManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        updateStatusBarColor(ContextCompat.getColor(requireContext(),R.color.primary_main))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleState()
        viewModel.setNotInternetMsg(getString(R.string.no_internet_connection))

    }

    override fun onResume() {
        super.onResume()
        viewModel.handelViewIntent()
    }

    override fun onStop() {
        super.onStop()
        viewModel.setErrorReason(ErrorModel(ErrorStatus.NO_DATA))
        viewModel.reset()
    }

    open fun onViewModelError() {}

    open fun handleUnAuthError() {

    }

    fun setActivityToolbarTitle(title: String, gravity: Int? = null) {
        toolbarListener?.setActivityToolbarTitle(title, gravity)
    }

    fun hideActivityToolbar() {
        toolbarListener?.hideActivityToolbar()
    }

    fun showActivityToolbar() {
        toolbarListener?.showActivityToolbar()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        showLoading(false)
    }


    open fun showLoading(show: Boolean) {
        mLoader?.showLoading(show)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let {
            if (it is LoadingListener) mLoader = it

            if (it is ToolbarListener) toolbarListener = it

            if (it is BottomNavListener) bottomNavListener = it
        }
    }

    fun toggleBottomNav(isVisible: Boolean) {
        bottomNavListener?.hideBottomNavListener?.value = isVisible
    }

    override fun onDetach() {
        super.onDetach()
        mLoader = null
        toolbarListener = null
        bottomNavListener = null
        _binding = null
    }

    open fun handleState() {
        lifecycleScope.launchWhenStarted {
            ensureActive()
            viewModel.state.collect {
                when (it) {
                    is UiState.Loading -> {
                        showLoading(it.Loading)
                    }

                    is UiState.Error -> {
                        handleErrorStatus(it.Error)
                    }

                    is UiState.CancellationMessage -> {
                        Log.d(TAG, it.CancellationMessage)
                    }

                    UiState.Idle -> {
                    }
                }
            }
        }
    }

    private fun handleErrorStatus(it: ErrorModel) {
        when (it.errorStatus) {
            ErrorStatus.NO_DATA -> {}

            ErrorStatus.NO_CONNECTION -> {
                baseNetworkingDialog.showDialog(
                    requireActivity(),
                    it.errorStatus
                )
            }

            ErrorStatus.UNAUTHORIZED -> handleUnAuthError()
            ErrorStatus.INTERNAL_SERVER_ERROR -> baseNetworkingDialog.showDialog(
                requireActivity(),
                it.errorStatus
            )

            ErrorStatus.UNKNOWN_HOST -> baseNetworkingDialog.showDialog(
                requireActivity(),
                it.errorStatus
            )

            ErrorStatus.FORRBIDEN, ErrorStatus.NOT_FOUND -> {
                requireContext().showToast("Something went wrong", ToastType.WARNING)
            }

            ErrorStatus.EMPTY_RESPONSE -> {
                requireContext().showToast("${it.message}", ToastType.WARNING)
            }

            else -> {
                requireContext().showToast("${it.message}", ToastType.WARNING)
            }
        }
        onViewModelError()
    }

    open fun handleEmptyResponseError(errorMessage: String?, errorStatus: ErrorStatus) {
        baseNetworkingDialog.showDialog(
            requireActivity(),
            errorStatus,
            errorMessage
        )
    }

    open fun hideKeyBoardOutSideTap(view: View) {
        view.hideKeyBoardOutSideTap()
    }


}