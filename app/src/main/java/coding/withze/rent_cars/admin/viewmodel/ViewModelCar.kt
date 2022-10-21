package coding.withze.rent_cars.admin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coding.withze.rent_cars.admin.model.ResponseAddcar
import coding.withze.rent_cars.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelCar : ViewModel() {

    lateinit var addLiveDataCar : MutableLiveData<ResponseAddcar>

    init {
        addLiveDataCar = MutableLiveData()
    }

    fun postLiveDataCar() : MutableLiveData<ResponseAddcar>{
        return addLiveDataCar
    }

    fun postApiCar(name : RequestBody, category : RequestBody, price : RequestBody, status : RequestBody, image : MultipartBody.Part){
        RetrofitClient.instance.addCarAdmin(name, category, price, status, image)
            .enqueue(object : Callback<ResponseAddcar>{
                override fun onResponse(
                    call: Call<ResponseAddcar>,
                    response: Response<ResponseAddcar>
                ) {
                    if (response.isSuccessful){
                        addLiveDataCar.postValue(response.body())
                    }else{
                        addLiveDataCar.postValue(null)
                    }

                }

                override fun onFailure(call: Call<ResponseAddcar>, t: Throwable) {
                    addLiveDataCar.postValue(null)
                }

            })
    }
}