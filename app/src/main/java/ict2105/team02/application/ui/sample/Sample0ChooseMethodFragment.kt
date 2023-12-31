package ict2105.team02.application.ui.sample

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import ict2105.team02.application.R
import ict2105.team02.application.databinding.FragmentSample0MethodBinding
import ict2105.team02.application.utils.Utils.Companion.showToast
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample0ChooseMethodFragment : Fragment() {
    private lateinit var binding: FragmentSample0MethodBinding
    private val sampleViewModel by activityViewModels<SampleViewModel>()

    private lateinit var sampleActivity: SampleActivity

    private companion object {
        //handle result of Camera permission
        private const val CAMERA_REQUEST_CODE = 1001
    }

    //URI of image
    private var imageUri: Uri? = null

    //array of permission required
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>

    private lateinit var progressDialog: ProgressDialog
    private lateinit var textRecognizer: TextRecognizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSample0MethodBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sampleActivity = requireActivity() as SampleActivity

        //init array of permission required for camera, gallery
        cameraPermission =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        storagePermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        binding.manualEntryButton.setOnClickListener {
            sampleActivity.changePage(1)
        }

        binding.inputImageButton.setOnClickListener {
            showInputImageDialog()
        }

        binding.processPhotoButton.setOnClickListener {
            if (imageUri == null) {
                showToast(requireContext(), "Pick Image First")
            } else {
                recognizeTextFromImage()
            }
        }
        binding.imageScrollView. visibility =View.GONE;
        binding.processPhotoButton.isEnabled = false
    }

    private fun recognizeTextFromImage() {
        progressDialog.setMessage("Preparing Image")
        progressDialog.show()
        try {
            val inputImage = InputImage.fromFilePath(requireContext(), imageUri!!)
            val sampleDataMap = hashMapOf<String, String>()
            progressDialog.setMessage("Recognizing Text")
            val textTaskResult = textRecognizer.process(inputImage)
                .addOnSuccessListener { text ->
                    progressDialog.dismiss()
                    val recognizedText = text.text
                    val lines =
                        recognizedText.split("\n") // Split the input string into lines using the newline character as the separator
                    for (line in lines) {
                        val parts =
                            line.split("=") // Split each line into parts using the colon character as the separator
                        if (parts.size == 2) { // Check if the line has exactly two parts
                            var key = parts[0].trim() // Remove whitespace around the key
                            key = key.replaceFirst(key[0], key[0].toLowerCase())
                            val value = parts[1].trim() // Remove whitespace around the value
                            sampleDataMap[key] = value
                        }
                    }
                    sampleViewModel.setAllSample(sampleDataMap)

                    sampleActivity.changePage(5)
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    showToast(requireContext(), "Failed due to ${e.message}")
                }
        } catch (e: Exception) {
            progressDialog.dismiss()
            showToast(requireContext(), "Failed due to ${e.message}")
        }
    }

    private fun showInputImageDialog() {
        PopupMenu(requireContext(), binding.inputImageButton).apply {
            menu.add(Menu.NONE, 1, 1, "Camera")
            menu.add(Menu.NONE, 2, 2, "From Gallery")
            setOnMenuItemClickListener { menuItem ->
                val id = menuItem.itemId
                if (id == 1) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_REQUEST_CODE
                        )
                    } else {
                        pickImageCamera()
                    }
                } else if (id == 2) {
                    pickImageGallery()
                }
                return@setOnMenuItemClickListener true
            }
        }.show()
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        onGalleryResult.launch(intent)
    }

    private val onGalleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            imageUri = data!!.data
            binding.photoImageView.setImageURI(imageUri)
            binding.inputImageButton.text = getString(R.string.scan_again)
            binding.processPhotoButton.isEnabled = true
            binding.imageScrollView.visibility = View.VISIBLE
        } else {
            showToast(requireContext(), "Cancelled")
        }
    }

    private fun pickImageCamera() {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "Sample Title")
            put(MediaStore.Images.Media.DESCRIPTION, "Sample Description")
        }

        imageUri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }

        onCameraResult.launch(intent)
    }

    private val onCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.photoImageView.setImageURI(imageUri)
            binding.inputImageButton.text = getString(R.string.scan_again)
            binding.processPhotoButton.isEnabled = true
        } else {
            showToast(requireContext(), resources.getString(R.string.scanner_close))
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageCamera()
            }
        }
    }
}