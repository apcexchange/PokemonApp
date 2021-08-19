package com.example.mypokemon.image_upload

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

const val BASE_URL = "https://darot-image-upload-service.herokuapp.com/api/v1/"

interface MyAPI {
    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("file") desc: RequestBody
    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): MyAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyAPI::class.java)
        }
    }
}