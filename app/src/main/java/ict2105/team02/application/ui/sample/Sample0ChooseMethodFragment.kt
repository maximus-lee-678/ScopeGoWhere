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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import ict2105.team02.application.databinding.FragmentSampleMethodBinding
import ict2105.team02.application.utils.Utils.Companion.showToast
import ict2105.team02.application.viewmodel.SampleViewModel

class Sample0ChooseMethodFragment : Fragment() {
    private lateinit var binding: FragmentSampleMethodBinding
    private lateinit var viewModel: SampleViewModel

    private companion object{
        //handle result of Camera permission
        private const val CAMERA_REQUEST_CODE = 1001
        private const val STORAGE_REQUEST_CODE = 200
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
        binding = FragmentSampleMethodBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[SampleViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val methodList:List<String> = listOf("Scan/ Take Photo", "Manual entry")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, methodList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sampleSpinner.adapter = adapter
        val spinner:Spinner = binding.sampleSpinner
//        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent?.getItemAtPosition(position) as String
//                if(selectedItem == "Scan/ Take Photo"){
//                    binding.photoView.visibility = View.VISIBLE
//                    binding.childFragmentContainer.visibility = View.GONE
//                    val childFragment = childFragmentManager.findFragmentById(binding.childFragmentContainer.id)
//                    if (childFragment != null) {
//                        childFragmentManager.beginTransaction().remove(childFragment).commit()
//                    }
//                } else{
//                    binding.photoView.visibility = View.GONE
//                    binding.childFragmentContainer.visibility = View.VISIBLE
//                    childFragmentManager.beginTransaction()
//                        .replace(binding.childFragmentContainer.id, FluidResultSampleFragment())
//                        .commit()
//                }
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }
        //init array of permission required for camera, gallery
        cameraPermission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        storagePermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        binding.inputImageBtn.setOnClickListener {
            showInputImageDialog()
        }
        binding.recognizeTextBtn.setOnClickListener {
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
            progressDialog.setMessage("Recognizing Text")
            val textTaskResult = textRecognizer.process(inputImage)
                .addOnSuccessListener { text->
                    progressDialog.dismiss()
                    val regonizedText = text.text
                    binding.recognizedTextEt.setText(regonizedText)
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
        popupMenu.menu.add(Menu.NONE,1,1,"CAMERA")
        popupMenu.menu.add(Menu.NONE,2,2,"GALLERY")

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