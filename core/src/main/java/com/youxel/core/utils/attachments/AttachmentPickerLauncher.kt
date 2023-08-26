package com.youxel.core.utils.attachments

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.youxel.core.domain.entities.enums.FilePickerAttachmentType

private const val ATTACKMENT_REGISTERY_KEY = "ATTACKMENT_REGISTERY_KEY"

class AttachmentPickerLauncher(
    private val context: Context,
    private val registry: ActivityResultRegistry,
    private val filePickerAttachmentType: FilePickerAttachmentType,
    private val onAttachmentSelected: (Boolean, List<Uri>, List<String>) -> Unit,
    private val registerKey:String=ATTACKMENT_REGISTERY_KEY
) : DefaultLifecycleObserver {

    private lateinit var getFile: ActivityResultLauncher<Array<String>>
    private lateinit var getMultipleFiles: ActivityResultLauncher<Array<String>>
    private lateinit var getDoc: ActivityResultLauncher<Array<String>>
    private lateinit var takePicture: ActivityResultLauncher<Uri>

    private lateinit var imageUri: Uri

    override fun onCreate(owner: LifecycleOwner) {
        when (filePickerAttachmentType) {
            FilePickerAttachmentType.ONE_FILE,
            FilePickerAttachmentType.OPEN_GALLERY,
            FilePickerAttachmentType.PDF -> {
                registerContentPickerLauncher(owner)
            }
            FilePickerAttachmentType.MULTIPLE_FILES -> {
                registerFilesPickerLauncher(owner)
            }
            FilePickerAttachmentType.DOCS -> {
                registerDocsPickerLauncher(owner)
            }
            FilePickerAttachmentType.OPEN_CAMERA -> {
                registerCameraLauncher(owner)
            }
        }
    }

    private fun registerContentPickerLauncher(owner: LifecycleOwner) {
        getFile = registry.register(
            registerKey,
            owner,
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            onLauncherResult(listOf(uri))
        }
    }

    private fun registerFilesPickerLauncher(owner: LifecycleOwner) {
        getMultipleFiles = registry.register(
            registerKey,
            owner,
            ActivityResultContracts.OpenMultipleDocuments()
        ) { listOfUri ->
            onLauncherResult(listOfUri)
        }
    }


    private fun registerDocsPickerLauncher(owner: LifecycleOwner) {
        getDoc = registry.register(
            registerKey,
            owner,
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            onLauncherResult(listOf(uri))
        }
    }

    private fun registerCameraLauncher(owner: LifecycleOwner) {
        takePicture = registry.register(
            registerKey,
            owner,
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                onAttachmentSelected(true, listOf(imageUri), emptyList())
            } else {
                onAttachmentSelected(false, emptyList(), emptyList())
            }
        }

    }

    fun getAttachment(owner: LifecycleOwner, tempFile: Uri? = null) {
        when (filePickerAttachmentType) {
            FilePickerAttachmentType.ONE_FILE -> {
                selectFile(owner)
            }
            FilePickerAttachmentType.PDF -> {
                selectPdf(owner)
            }
            FilePickerAttachmentType.OPEN_GALLERY -> {
                selectImageFromGallery(owner)
            }
            FilePickerAttachmentType.MULTIPLE_FILES -> {
                selectFiles(owner)
            }
            FilePickerAttachmentType.DOCS -> {
                selectDoc(owner)
            }
            FilePickerAttachmentType.OPEN_CAMERA -> {
                tempFile?.let {
                    imageUri = it
                    selectImageFromCamera(imageUri)
                }
            }
        }
    }

    private fun selectFile(owner: LifecycleOwner) {
        if (!this::getFile.isInitialized) {
            registerContentPickerLauncher(owner)
        }
        getFile.launch(
            arrayOf(
                "image/*",
                "application/pdf",
                "application/msword",
                "application/ms-doc",
                "application/doc",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "application/docx",
                "text/plain"
            )
        )
    }

    private fun selectImageFromGallery(owner: LifecycleOwner) {
        if (!this::getFile.isInitialized) {
            registerContentPickerLauncher(owner)
        }
        getFile.launch(arrayOf("image/*"))
    }

    private fun selectPdf(owner: LifecycleOwner) {
        if (!this::getFile.isInitialized) {
            registerContentPickerLauncher(owner)
        }
        getFile.launch(arrayOf("application/pdf"))
    }

    private fun selectFiles(owner: LifecycleOwner) {
        if (!this::getMultipleFiles.isInitialized) {
            registerFilesPickerLauncher(owner)
        }
        getMultipleFiles.launch(
            arrayOf(
                "image/*",
                "application/pdf",
                "application/msword",
                "application/ms-doc",
                "application/doc",
                "application/docx",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "text/plain"
            )
        )
    }

    private fun selectDoc(owner: LifecycleOwner) {
        if (!this::getDoc.isInitialized) {
            registerDocsPickerLauncher(owner)
        }
        getDoc.launch(
            arrayOf(
                "application/pdf",
                "application/msword",
                "application/ms-doc",
                "application/doc",
                "application/docx",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "text/plain"
            )
        )
    }


    private fun selectImageFromCamera(tempFile: Uri) {
        tempFile.let { uri ->
            takePicture.launch(uri)
        }
    }

    private fun onLauncherResult(filesList: List<Uri?>) {
        val uriList = mutableListOf<Uri>()
        val pathsList = mutableListOf<String>()
        if (filesList.any { uri: Uri? -> uri == null }) {
            onAttachmentSelected(false, emptyList(), emptyList())
        } else {
            for (item in filesList) {
                uriList.add(item!!)
                val filePath = RealPathUtil.getRealPathFromURI(context, item)
                filePath?.let {
                    pathsList.add(it)
                }
            }
            onAttachmentSelected(true, uriList, pathsList)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        when (filePickerAttachmentType) {
            FilePickerAttachmentType.ONE_FILE,
            FilePickerAttachmentType.OPEN_GALLERY,
            FilePickerAttachmentType.PDF -> {
                getFile.unregister()
            }
            FilePickerAttachmentType.MULTIPLE_FILES -> {
                getMultipleFiles.unregister()
            }
            FilePickerAttachmentType.DOCS -> {
                getDoc.unregister()
            }
            FilePickerAttachmentType.OPEN_CAMERA -> {
                takePicture.unregister()
            }
        }
    }
}