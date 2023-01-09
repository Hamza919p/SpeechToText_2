package com.project.speechtotext.ui.fragments.homeChildFragments

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.project.speechtotext.Permissions
import com.project.speechtotext.R
import com.project.speechtotext.databinding.FragmentDocumentScanBinding
import java.io.File
import java.io.IOException

class DocumentScanFragment private constructor() : Fragment() {
    private lateinit var binding: FragmentDocumentScanBinding
    private var imageUri: Uri? = null
    private var recognizer: TextRecognizer? = null

    companion object {
        private var instance: DocumentScanFragment? = null

        @Synchronized
        fun getInstance(): DocumentScanFragment? {
            if (instance == null) instance = DocumentScanFragment()
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_document_scan, container, false)
        init()
        return binding.root
    }

    private fun init() {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        binding.ivScanner.setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        if (!Permissions.checkCameraPermission(requireContext())) {
            mPermissionResult.launch(Manifest.permission.CAMERA)
        } else {
            imageUri = createImageUri()
            cameraResultLauncher.launch(imageUri)
        }
    }

    private val mPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            imageUri = createImageUri()
            cameraResultLauncher.launch(imageUri)
        }
    }

    private val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                getTextFromImage()
            }
        }

    private fun getTextFromImage() {
        var image: InputImage? = null
        try {
            image = InputImage.fromFilePath(requireContext(), imageUri!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (image != null) {
            recognizer?.process(image!!)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    parseText(it);
                } else {
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun parseText(it: Task<Text>) {
        val resultText = it.result.text
        for (block in it.result.textBlocks) {
            val blockText = block.text
            val blockCornerPoints = block.cornerPoints
            val blockFrame = block.boundingBox
            for (line in block.lines) {
                val lineText = line.text
                val lineCornerPoints = line.cornerPoints
                val lineFrame = line.boundingBox
                for (element in line.elements) {
                    val elementText = element.text
                    val elementCornerPoints = element.cornerPoints
                    val elementFrame = element.boundingBox
                }
            }
        }
        binding.tvImageText.visibility = View.VISIBLE
        binding.tvImageText.text = resultText
    }

    private fun createImageUri(): Uri? {
        // PERMISSION GRANTED
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        return FileProvider.getUriForFile(
            requireContext(),
            "com.project.speechtotext.fileProvider",
            photoFile
        )
    }

}