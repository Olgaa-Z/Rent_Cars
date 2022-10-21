package coding.withze.rent_cars.network

import coding.withze.rent_cars.admin.model.ResponseAddcar
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("admin/car")
    @Multipart
    fun addCarAdmin(
        @Part("name") name: RequestBody,
        @Part("category") category : RequestBody,
        @Part("price") price : RequestBody,
        @Part("status") status : RequestBody,
        @Part fileImage : MultipartBody.Part
    ): Call<ResponseAddcar>

}