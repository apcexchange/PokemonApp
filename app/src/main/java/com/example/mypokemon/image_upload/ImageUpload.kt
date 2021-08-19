package com.example.mypokemon.image_upload


import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mypokemon.R
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageUpload : AppCompatActivity(), UploadRequestBody.UploadCallback {
    lateinit var image_view:ImageView
    lateinit var button_upload:Button
    lateinit var progressBar:ProgressBar
    lateinit var root_layout : ConstraintLayout
//    lateinit var root_layout : ConstraintLayout
    var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)

                progressBar = findViewById(R.id.progress_bar)
                button_upload = findViewById(R.id.button_upload)
                root_layout = findViewById(R.id.root_layout)
                image_view = findViewById(R.id.image_view)

        image_view.setOnClickListener {
            openImageChooser()
        }

        button_upload.setOnClickListener {
            uploadImage()
        }


    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            root_layout.snackbar("Select an Image First")
            return
        }


        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        progressBar.progress = 0
        val body = UploadRequestBody(file, "image", this)
        MyAPI().uploadImage(
            MultipartBody.Part.createFormData(
                "file",
                file.name,
                body
            ),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "json")
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                root_layout.snackbar(t.message!!)
                progressBar.progress = 0
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    root_layout.snackbar(it.message)
                    progressBar.progress = 100
                }
            }
        })
    }


    private fun openImageChooser(){
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    image_view.setImageURI(selectedImageUri)
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

    override fun onProgressUpdate(percentage: Int) {
        progressBar.progress = percentage
    }

    fun View.snackbar(message: String) {
        Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }

    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

}