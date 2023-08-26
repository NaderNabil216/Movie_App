package com.youxel.core.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.youxel.core.R


/**
 * Created by Mohamed Assem Ali on 28 Jul 2022.
 */

const val REQUEST_CODE_PERMISSIONS = 100
const val REQUEST_CODE_FILES_PERMISSIONS = 101


fun Activity.isPermissionsGranted(
    permission: String,
): Boolean {
    val isGranted = ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
    if (!isGranted) {
        this.showRationaleDialog()
    }
    return isGranted
}

fun Activity.showRationaleDialog() {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setCancelable(false)
    val customView: View =
        LayoutInflater.from(this).inflate(R.layout.layout_permission_rationale, null)
    dialogBuilder.setView(customView)
    val alertDialog: AlertDialog = dialogBuilder.create()
    val okayButton: TextView = customView.findViewById(R.id.tvOk)
    val cancelButton: TextView = customView.findViewById(R.id.tvCancel)
    okayButton.setOnClickListener {
        openAppSettings()
        alertDialog.dismiss()
    }
    cancelButton.setOnClickListener {
        alertDialog.dismiss()
    }
    alertDialog.show()
}

fun Activity.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:" + this.packageName)
    startActivity(intent)
}

fun Activity.requestPermissionWithRationale(
    permission: String,
    snackbar: Snackbar
) {
    val provideRationale = shouldShowRequestPermissionRationale(permission)

    if (provideRationale) {
        snackbar.show()
    } else {
        requestPermissions(arrayOf(permission), REQUEST_CODE_PERMISSIONS)
    }
}

fun Activity.requestFilesPermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse(String.format("package:%s", this.packageName))
            this.startActivityForResult(intent, REQUEST_CODE_FILES_PERMISSIONS)
        } catch (e: Exception) {
            Log.e("PermissionsUtils", "requestFilesPermissions: ${e.message}")
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            this.startActivityForResult(intent, REQUEST_CODE_FILES_PERMISSIONS)
        }
    } else {
        this.requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_FILES_PERMISSIONS
        )
    }
}

fun Activity.hasFilesPermissions(): Boolean {
    val isPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        this.isPermissionsGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    if (!isPermissionGranted) {
        this.requestFilesPermissions()
    }
    return isPermissionGranted
}
