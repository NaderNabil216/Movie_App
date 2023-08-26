package com.youxel.core.base.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.youxel.core.R
import com.youxel.core.base.fragment.BaseUiHelper
import com.youxel.core.base.fragment.Inflate
import com.youxel.core.base.view_model.BaseViewModel
import com.youxel.core.base.view_model.UiState
import com.youxel.core.domain.entities.base.ErrorModel
import com.youxel.core.domain.entities.base.ErrorStatus
import com.youxel.core.utils.LoadingListener
import com.youxel.core.utils.showToast
import com.youxel.navigation.LoginNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ensureActive
import javax.inject.Inject


@ExperimentalCoroutinesApi
abstract class BaseBottomSheetFragment<VB : ViewBinding, VM : BaseViewModel, HelperClass : BaseUiHelper>
    (private val inflate: Inflate<VB>) : BottomSheetDialogFragment() {

    private val TAG = BaseBottomSheetFragment::class.java.simpleName

    @Inject
    lateinit var baseNetworkingDialog: BaseNetworkingDialog

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var mLoader: LoadingListener? = null

    abstract val viewModel: VM

    open var bottomSheetBehaviorState = BottomSheetBehavior.STATE_COLLAPSED

    open var isRoundedCorners = true

    open var isDialogCancelable = true

    open var defaultPeekHeight = 1000

    abstract val fragmentHelper: HelperClass

    @Inject
    lateinit var loginNavigation: LoginNavigation


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleState()
    }

    fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams: ViewGroup.LayoutParams? = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    open fun handleUnAuthError() {
       requireContext().showToast("Authorization Issue")
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.attributes?.windowAnimations = R.style.dialog_animation
            setOnShowListener { setupBottomSheet(it) }

        }
    }

    open fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        ) ?: return

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = bottomSheetBehaviorState
        bottomSheetBehavior.peekHeight = defaultPeekHeight
        bottomSheetDialog.setCancelable(isDialogCancelable)

        if (isRoundedCorners) bottomSheet.setBackgroundResource(R.drawable.bg_rounded_sheet_white)
        else bottomSheet.setBackgroundResource(R.drawable.bg_transeperent)
    }

    open fun showLoading(show: Boolean) {
        mLoader?.showLoading(show)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let {
            if (it is LoadingListener) mLoader = it
        }
    }

    override fun onDetach() {
        super.onDetach()
        mLoader = null
        showLoading(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        showLoading(false)
        _binding = null
    }

    private fun handleState() {
        lifecycleScope.launchWhenStarted {
            ensureActive()
            viewModel.state.collect {
                ensureActive()
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
                    else -> {}
                }
            }
        }
    }

    private fun handleErrorStatus(it: ErrorModel) {
        when (it.errorStatus) {
            ErrorStatus.NO_DATA -> {
            }
            ErrorStatus.NO_CONNECTION -> {

                baseNetworkingDialog.showDialog(
                    requireActivity(), it.errorStatus
                )
            }
            ErrorStatus.UNAUTHORIZED -> handleUnAuthError()
            ErrorStatus.INTERNAL_SERVER_ERROR -> baseNetworkingDialog.showDialog(
                requireActivity(), it.errorStatus
            )
            ErrorStatus.UNKNOWN_HOST -> baseNetworkingDialog.showDialog(
                requireActivity(), it.errorStatus
            )
            ErrorStatus.FORRBIDEN, ErrorStatus.NOT_FOUND -> Toast.makeText(
                context, "Forbidden or not found", Toast.LENGTH_SHORT
            ).show()

            ErrorStatus.EMPTY_RESPONSE -> Toast.makeText(
                context, "${it.message}", Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.handelViewIntent()
    }
}
