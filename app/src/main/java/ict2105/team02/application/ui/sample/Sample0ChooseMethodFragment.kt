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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import ict2105.team02.application.databinding.FragmentSample0MethodBinding
import ict2105.team02.application.utils.Utils.Companion.showToast
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample0ChooseMethodFragment : Fragment() {
    private lateinit var binding: FragmentSample0MethodBinding
    private val viewModel by activityViewModels<SampleViewModel>()
    private lateinit var sampleActivity:SampleActivity
    private companion object{
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
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSample0MethodBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sampleActivity = requireActivity() as SampleActivity

        //init array of permission required for camera, gallery
        cameraPermission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        storagePermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        binding.manualEntryButton.setOnClickListener {
            sampleActivity.changePage(1)
        }
        binding.photoEntryButton.setOnClickListener {
            binding.buttonOptionsLayout.visibility = View.GONE
            binding.methodSelectTextView.text = "Select photo source and then scan"
            binding.photoView.visibility = View.VISIBLE
        }

        binding.inputImageBtn.setOnClickListener {
            showInputImageDialog()
        }
        binding.moveToReview.setOnClickListener {
            if (imageUri == null){
                showToast(requireContext(),"Pick Image First")
            } else{
                recognizeTextFromImage()
            }
        }
    }

    private fun recognizeTextFromImage() {
        progressDialog.setMessage("Preparing Image")
        progressDialog.show()
        try{
            val inputImage = InputImage.fromFilePath(requireContext(), imageUri!!)
            val sampleDataMap = hashMapOf<String, String>()
            progressDialog.setMessage("Recognizing Text")
            val textTaskResult = textRecognizer.process(inputImage)
                .addOnSuccessListener { text->
                    progressDialog.dismiss()
                    val recognizedText = text.text
                    val lines = recognizedText.split("\n") // Split the input string into lines using the newline character as the separator
                    for (line in lines) {
                        val parts = line.split("=") // Split each line into parts using the colon character as the separator
                        if (parts.size == 2) { // Check if the line has exactly two parts
                            var key = parts[0].trim() // Remove whitespace around the key
                            key = key.replaceFirst(key[0], key[0].toLowerCase())
                            val value = parts[1].trim() // Remove whitespace around the value
                            sampleDataMap[key] = value
                            Log.d("Check Text From Image", "$key = ${sampleDataMap[key]}")
                        }
                        else{
                            Log.d("Value Read", line)
                        }
                    }
                    viewModel.setAllSample(sampleDataMap)

                    sampleActivity.changePage(5)
                }
                .addOnFailureListener { e->
                    progressDialog.dismiss()
                    showToast(requireContext(),"Failed due to ${e.message}")
                }
        } catch (e: Exception){
            progressDialog.dismiss()
            showToast(requireContext(),"Failed due to ${e.message}")
        }
    }
    private fun showInputImageDialog(){
        val popupMenu = PopupMenu(requireContext(), binding.inputImageBtn)
        popupMenu.menu.add(Menu.NONE,1,1,"Camera")
        popupMenu.menu.add(Menu.NONE,2,2,"From Gallery")

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { menuItem->
            val id = menuItem.itemId
            if(id == 1){
                if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
                } else {
                    pickImageCamera()}
            } else if (id ==2){
                pickImageGallery()
            }

            return@setOnMenuItemClickListener true
        }
    }
    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            imageUri = data!!.data
            binding.imageIv.setImageURI(imageUri)
        } else{
            showToast(requireContext(),"Cancelled")
        }
    }

    private fun pickImageCamera(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Sample Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description")
        imageUri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityLauncher.launch(intent)
    }

    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                binding.imageIv.setImageURI(imageUri)
            } else{
                showToast(requireContext(),"Cancelled")
            }
        }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty()){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImageCamera()
            } else{
                showToast(requireContext(),"What is happening")
            }
        }
    }
}