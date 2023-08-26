package com.youxel.core.base.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.youxel.core.utils.showRationaleDialog


/**
 * Created by Shehab Elsarky
 */
abstract class PermissionsActivity : BaseActivity() {
    abstract var permissions: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissionRequired()
    }

    val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (!granted) {
                showRationaleDialog()
            }
        }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun checkPermissionRequired() {
        if (!hasPermissions(this, permissions)) {
            requestPermissionsLauncher.launch(
                permissions
            )
        }

    }
}

