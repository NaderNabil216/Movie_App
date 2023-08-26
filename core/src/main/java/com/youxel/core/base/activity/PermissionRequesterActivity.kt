package com.youxel.core.base.activity

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.youxel.core.utils.showRationaleDialog

abstract class PermissionRequesterActivity : AppCompatActivity() {
    abstract var permissions: Array<String>

    val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsResult ->
            val isAllGranted = permissionsResult.values.all { true }
            if (!isAllGranted) {
                showRationaleDialog()
            }
        }
}