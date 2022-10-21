package coding.withze.rent_cars.admin

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import coding.withze.rent_cars.R
import coding.withze.rent_cars.admin.viewmodel.ViewModelCar
import coding.withze.rent_cars.databinding.ActivityAddCarBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddCarActivity : AppCompatActivity() {

    private var imageMultiPart: MultipartBody.Part? = null
    private var imageUri: Uri? = Uri.EMPTY
    private var imageFile: File? = null
    lateinit var viewModelCar : ViewModelCar

    lateinit var binding: ActivityAddCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelCar = ViewModelProvider(this).get(ViewModelCar::class.java)

        binding.addImage.setOnClickListener {
            openGallery()
        }
        binding.postDataCar.setOnClickListener {
            postDataCar()
        }
    }

    fun postDataCar(){
        val name = binding.nameCar.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val category = binding.categoryCar.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val price = binding.priceCar.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val status = binding.statusCar.text.toString().toRequestBody("multipart/form-data".toMediaType())

        viewModelCar.addLiveDataCar.observe(this,{
            if (it != null){
                Toast.makeText(this, "Add Data Car Succeeded", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelCar.postApiCar(name,category,price,status, imageMultiPart!!)
    }

    fun openGallery(){
        getContent.launch("image/*")
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = this!!.contentResolver
                val type = contentResolver.getType(it)
                imageUri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.addImage.setImageURI(it)
                Toast.makeText(this, "$imageUri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use    { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultiPart = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }

}